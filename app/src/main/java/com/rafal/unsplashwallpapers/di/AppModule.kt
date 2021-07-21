package com.rafal.unsplashwallpapers.di

import com.rafal.unsplashwallpapers.model.UnsplashApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val CLIENT_ID = "8sL0GsM0b1ltBpRFPyEFiB5yk_cAxZz107KpgSjSMkI"
private const val BASE_URL = "https://api.unsplash.com/"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("Authorization", "Client-ID $CLIENT_ID")
                    builder.header("Accept-Version", "v1")
                    chain.proceed(builder.build())
                }
            )
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit) : UnsplashApi {
        return retrofit.create(UnsplashApi::class.java)
    }
}