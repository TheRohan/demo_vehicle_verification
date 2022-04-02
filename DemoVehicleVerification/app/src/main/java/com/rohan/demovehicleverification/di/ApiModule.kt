package com.rohan.demovehicleverification.di

import com.rohan.demovehicleverification.data.network.VehicleNetworkServices
import com.rohan.demovehicleverification.other.Constants.API_KEY
import com.rohan.demovehicleverification.other.Constants.ENDPOINT
import com.rohan.demovehicleverification.repositories.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(60, TimeUnit.SECONDS) // connect timeout
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()

                requestBuilder.addHeader("Content-type", "application/json")
                requestBuilder.addHeader("x-api-key", API_KEY)
                val request = requestBuilder
                    .method(original.method, original.body)
                    .build()

                val response = chain.proceed(request)
                response
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieAppService(retrofit: Retrofit): VehicleNetworkServices {
        return retrofit.create(VehicleNetworkServices::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkRepository(vehicleNetworkServices: VehicleNetworkServices): NetworkRepository {
        return NetworkRepository(vehicleNetworkServices)
    }


}