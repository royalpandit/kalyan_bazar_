package com.app.kalyanbazar.di

import android.content.Context
import com.app.kalyanbazar.data.network.ApiInterface
import com.app.kalyanbazar.data.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
 import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
//scope
object AppModule {

    @Singleton
    @Provides
    fun provideAuthApi(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): ApiInterface {
        return remoteDataSource.buildApi(ApiInterface::class.java, context)
    }

   // @Singleton
    /*@Provides
    fun provideAuthApiZ(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): ApiInterface {
        return remoteDataSource.projectApi(ApiInterface::class.java, context)
    }*/





//    @Provides
//    fun providesUserDao(userDatabase: UserDatabase): UserDao = userDatabase.userDao()
//
//    @Provides
//    @Singleton
//    fun providesUserDatabase(@ApplicationContext context: Context): UserDatabase
//            = Room.databaseBuilder(context, UserDatabase::class.java,"UserDatabase").build()
//
//    @Provides
//    fun providesUserRepository(userDao: UserDao) : DBRepository
//            = DBRepository(userDao)

}