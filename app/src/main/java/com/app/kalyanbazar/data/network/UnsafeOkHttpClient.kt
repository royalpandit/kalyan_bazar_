package com.app.kalyanbazar.data.network

import android.annotation.SuppressLint
import com.app.kalyanbazar.BuildConfig
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.net.CookieHandler
import java.net.CookieManager
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


object UnsafeOkHttpClient {
  //  val Token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiI1IiwiZ2l2ZW5fbmFtZSI6IkFiaGlzaGVrIiwiZmFtaWx5X25hbWUiOiJTaGFybWEiLCJlbWFpbCI6ImFAeW9wbWFpbC5jb20iLCJuYmYiOjE2NTQ3NTczMTksImV4cCI6MTY1NDc2MDkxOSwiaWF0IjoxNjU0NzU3MzE5fQ.xY4iRRt2Ne4lcKcFaJeNGUfwpGfnAQ4T7x3vzGBNGwU"
    fun getUnsafeOkHttpClient(): OkHttpClient? {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(@SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(c: Array<X509Certificate>, a: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(c: Array<X509Certificate>, a: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            })

            // Install the all-trusting trust manager
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val interceptor =
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                builder.addInterceptor(interceptor)
            }
            val cookieHandler: CookieHandler = CookieManager()
            builder.cookieJar(JavaNetCookieJar(cookieHandler))
                .cache(MyApplication.instance?.let {
                    Cache(
                        it.cacheDir,
                        (10 * 1024 * 1024).toLong()
                    )
                }) // 10 MB
                .connectTimeout(1, TimeUnit.MINUTES)
                .addNetworkInterceptor(AddHeaderInterceptor())
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(object : HostnameVerifier {
                override fun verify(p0: String?, p1: SSLSession?): Boolean {
                    return true
                }

            })
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private class AddHeaderInterceptor : Interceptor {
        @kotlin.jvm.Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
            builder.addHeader("Accept", "application/json; charset=utf-8")


               val token = MyApplication.tinyDB.getString(
                   Constants.SharedPref.ACCESS_TOKEN,
                   "not_selected"
               )
             if (!token.isNullOrEmpty())
                builder.addHeader(
                    "Authorization",
//                    "Bearer " + AES.decrypt(token, Constants.SharedPref.ACCESS_TOKEN)
                    "Bearer $token"
                )
            return chain.proceed(builder.build())
        }
    }


}