package com.danijax.albums.service

import android.util.Log
import android.util.Log.INFO
import com.google.gson.Gson
import me.sianaki.flowretrofitadapter.FlowCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Level.INFO

object HttpProvider {

    private var okHttpClient: OkHttpClient? = null
    private const val REQUEST_TIMEOUT = 3L

    private fun logger(): HttpLoggingInterceptor{
        val  logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.i("HTTP CLient", it)
        })
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return logging
    }

    private fun getOkHttpClient(): OkHttpClient {
        return if (okHttpClient == null) {
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logger())
                .build()
            this.okHttpClient = okHttpClient
            okHttpClient
        } else {
            okHttpClient!!
        }
    }

    fun client(): AlbumsApiService {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .client(getOkHttpClient())
            .build()
            .create(AlbumsApiService::class.java)
    }


}