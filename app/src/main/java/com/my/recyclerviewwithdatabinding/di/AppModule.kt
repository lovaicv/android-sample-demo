package com.my.recyclerviewwithdatabinding.di

import android.content.Context
import androidx.room.Room
import com.my.recyclerviewwithdatabinding.BuildConfig
import com.my.recyclerviewwithdatabinding.api.AccessKeyInterceptor
import com.my.recyclerviewwithdatabinding.api.ApiHelper
import com.my.recyclerviewwithdatabinding.api.ApiHelperImpl
import com.my.recyclerviewwithdatabinding.api.ApiService
import com.my.recyclerviewwithdatabinding.database.AppDatabase
import com.my.recyclerviewwithdatabinding.database.CurrencyDao
import com.my.recyclerviewwithdatabinding.database.CurrencyRepository
import com.my.recyclerviewwithdatabinding.other.Constants
import com.my.recyclerviewwithdatabinding.utils.ConnectionChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAccessKeyInterceptor(): AccessKeyInterceptor {
        return AccessKeyInterceptor("ac7ff6ff2b21303741585f5e04adcc8f")
    }

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(accessKeyInterceptor: AccessKeyInterceptor) = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(accessKeyInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_db"
        ).build()
    }

    @Provides
    fun provideCurrencyDao(appDatabase: AppDatabase): CurrencyDao {
        return appDatabase.currencyDao()
    }

    @Provides
    fun provideCurrencyRepository(currencyDao: CurrencyDao): CurrencyRepository {
        return CurrencyRepository(currencyDao)
    }

    @Provides
    fun provideConnectionChecker(context: Context): ConnectionChecker {
        return ConnectionChecker(context)
    }

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}