package com.example.swipeassignment.dependency_injection

import com.example.swipeassignment.api.ProductApiService
import com.example.swipeassignment.repo_section.Repo
import com.example.swipeassignment.ui.products_screen.ProductsScreenRepo
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Module:- It is a part of Dagger Hilt. And module is a central place where we tell
 * hilt how to create this dependency. Like in below case-
 *  -How to create retrofit instance
 *  -How to create api.
 *  -And finally how to create repo's instance.
 */
@Module
@InstallIn(SingletonComponent::class)
object Module {

    /**
     * GetLoggingInterceptor: This interceptor is used to see what info we send to server when we do add and get request
     *  into logcat. Like what header (if any) we passed, what are the response code we get from server whether it is
     *  in Success case or in failed case.
     *  So overall shows all info into logcat.
     *  HttpLoggingInterceptor.Level.BODY:- This is type of level means what level of info we want to see like whole body or some context.
     */
    private fun getLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
    }

    /**
     * To used interceptor we used OkHttpClient because it is part of OkHttpClient.
     */
    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(getLoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofitInstance(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://app.getswipe.in/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesRepoInstance(productApiService: ProductApiService): Repo{
        return ProductsScreenRepo(productApiService = productApiService)
    }
}