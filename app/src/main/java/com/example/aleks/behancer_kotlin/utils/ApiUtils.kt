package com.example.aleks.behancer_kotlin.utils

import com.example.aleks.behancer_kotlin.BuildConfig
import com.example.aleks.behancer_kotlin.data.api.ApiKeyInterceptor
import com.example.aleks.behancer_kotlin.data.api.BehanceApi
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

val networkExceptions =
    listOf(UnknownHostException::class, SocketTimeoutException::class, ConnectException::class)

object ApiUtils {
    private lateinit var client: OkHttpClient
    private lateinit var retrofit: Retrofit
    private lateinit var gson: Gson
    private lateinit var api: BehanceApi


    private fun initClient(): OkHttpClient {
        if (!::client.isInitialized) {
            val builder = OkHttpClient().newBuilder()
            builder.addInterceptor(ApiKeyInterceptor())
            if (BuildConfig.BUILD_TYPE.contains("realise")) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(interceptor)
            }
            client = builder.build()
        }
        return client
    }

    private fun initRetrofit():Retrofit{
        if (!::gson.isInitialized){
            gson = Gson()
        }
        if (!::retrofit.isInitialized){
            retrofit = Retrofit.Builder().baseUrl(BuildConfig.API_URL)
                .client(initClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit
    }

    fun initApiService():BehanceApi{
        if (!::api.isInitialized){
            api = initRetrofit().create(BehanceApi::class.java)
        }
        return api
    }
}
