package com.example.todoappmvvm.model.retrofit

import android.content.Context
import com.example.todoappmvvm.SharedPreferencesHelper
import com.example.todoappmvvm.view.activity.LoginActivity
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor to add auth token to requests
 */
class AuthInterceptor(context: Context) : Interceptor {
    private val sessionManager = SharedPreferencesHelper(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())

    }
}