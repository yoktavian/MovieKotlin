package com.yoktavian.moviekotlin.remote.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {
    companion object {
        fun create() : ApiClient {
            val apiClient : ApiClient by lazy {
                val retrofit = Retrofit
                        .Builder()
                        .baseUrl("https://api.themoviedb.org")
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                retrofit.create(ApiClient::class.java)
            }
            return apiClient
        }
    }
}