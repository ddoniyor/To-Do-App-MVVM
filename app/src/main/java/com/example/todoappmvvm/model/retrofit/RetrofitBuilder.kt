package com.example.todoappmvvm.model.retrofit

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    private lateinit var apiInterface: ApiInterface
    private val BASE_URL ="http://37.98.152.178:7002/"

    fun getApiInterface(context: Context): ApiInterface {
        //initialize ApiInterface if not init yet
        if (!::apiInterface.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context))
                .build()
            apiInterface = retrofit.create(ApiInterface::class.java)
        }
        return apiInterface
    }


    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }
}