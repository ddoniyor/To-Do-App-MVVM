package com.example.todoappmvvm

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {


    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val LOGIN_KEY = "login_key"
    }



    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun clearDataShared() {
        prefs.edit().remove(USER_TOKEN).commit()
    }



    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }


    fun fetchStateAct(value: Boolean): Boolean {
        return prefs.getBoolean(LOGIN_KEY, value)
    }

    fun saveStateAct(value: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(LOGIN_KEY, value)
        editor.apply()
    }

    fun clearDataSharedLogin() {
        prefs.edit().remove(LOGIN_KEY).commit()
    }
}
