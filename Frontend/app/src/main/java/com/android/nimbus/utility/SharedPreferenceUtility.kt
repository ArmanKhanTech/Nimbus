package com.android.nimbus.utility

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtility(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("Nimbus", Context.MODE_PRIVATE)

    fun saveBooleanData(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBooleanData(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun saveStringData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringData(key: String, defaultValue: String): String? {
        return sharedPreferences.getString(key, defaultValue)
    }
}