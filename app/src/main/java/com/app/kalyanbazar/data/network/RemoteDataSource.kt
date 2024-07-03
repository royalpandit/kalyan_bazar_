package com.app.kalyanbazar.data.network

import android.content.Context
import android.util.Log
import com.app.kalyanbazar.BuildConfig
import com.app.kalyanbazar.utils.Constants
import com.app.kalyanbazar.utils.MyApplication

import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.CookieHandler
import java.net.CookieManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class RemoteDataSource @Inject constructor() {

/*
    fun <Api> buildApi(
        api: Class<Api>,
        context: Context
    ): Api {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
       .baseUrl("http://solar.betablackboard.in/")
            .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(api)
    }*/
fun <Api> buildApi(
    api: Class<Api>,
    context: Context
): Api {
    val gson = GsonBuilder().setLenient().create()
    return Retrofit.Builder()
        //.baseUrl("http://16.170.7.195/v1/")
        .baseUrl("http://api.kalyanbazar.co.in/v1/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(api)

}
    private val httpClient: OkHttpClient
        get() {
            val cookieHandler: CookieHandler = CookieManager()
            val client = OkHttpClient().newBuilder().cookieJar(JavaNetCookieJar(cookieHandler))
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
            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(interceptor)
            }
            //     MyApplication.instance?.let {client.addInterceptor( ChuckerInterceptor(it) )}
            return client.build()
        }

  /*  private class AddHeaderInterceptor : Interceptor {
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
    }*/
  class BasicAuthInterceptor(username: String, password: String): Interceptor {
      private var credentials: String = Credentials.basic(username, password)

      override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
          var request = chain.request()
          request = request.newBuilder().header("Authorization", credentials).build()
          return chain.proceed(request)
      }
  }

    private class AddHeaderInterceptor : Interceptor {
        @kotlin.jvm.Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
            val client =  OkHttpClient.Builder()
                .addInterceptor(BasicAuthInterceptor("admin",     "Admin@123"))
            builder.addHeader("Accept", "application/json; charset=utf-8")
            val token = MyApplication.tinyDB.getString(Constants.SharedPref.ACCESS_TOKEN, "")
            val deviceToken =  MyApplication.tinyDB.getString(Constants.SharedPref.FIREBASE_TOKEN, "")

            Log.e("toke","TOken==>>>$deviceToken")
             if (!token.isNullOrEmpty())
                builder.addHeader(
                    "AccessToken",
//                    "Bearer " + AES.decrypt(token, Constants.SharedPref.ACCESS_TOKEN)
                    "$token"
                ).addHeader("Devicetoken","$deviceToken")
            return chain.proceed(builder.build())
        }
    }
}