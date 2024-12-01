package com.example.userlookup

import android.app.Application
import android.content.SharedPreferences
import com.example.userlookup.common.SqlDelightInitializer

import com.example.userlookup.util.AppConstants
import kotlin.text.StringBuilder

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        SqlDelightInitializer.initialize(this)
    }
    companion object {
        var instance: BaseApplication? = null
    }
    fun getSharedPreference(): SharedPreferences {
        return getSharedPreferences(AppConstants.USER_LOOK_UP, 0) //No I18N
    }

    fun setLongValueIntoPref(key: String,value: Long) {
        val editor = getSharedPreference().edit()
        editor.putLong(key, value)
        editor.apply()
    }
    private fun getLongValueFromPref(key: String): Long = getSharedPreference().getLong(key, 0L)

    fun getLastSyncTimeBasedOnPreference(key: String,itemId:String = AppConstants.EMPTY_VALUE): Long {
        val keyBuilder = StringBuilder(key)
        if(itemId.isNotEmpty()){
            keyBuilder.append("_").append(itemId)
        }
        return getLongValueFromPref(keyBuilder.toString())
    }
    fun setLastSyncTimeBasedOnPreference(key: String,itemId:String = AppConstants.EMPTY_VALUE,value: Long) {
        val keyBuilder = StringBuilder(key)
        if(itemId.isNotEmpty()){
            keyBuilder.append("_").append(itemId)
        }
        return setLongValueIntoPref(keyBuilder.toString(),value)
    }


}

//fun setBooleanValueIntoPref(key: String,value: Boolean) {
//    val editor = getSharedPreference().edit()
//    editor.putBoolean(key, value)
//    editor.apply()
//}
//fun getBooleanValueIntoPref(key: String): Boolean = getSharedPreference().getBoolean(key, false)
//fun setStringValueIntoPref( key: String,value: String) {
//    val editor = getSharedPreference().edit()
//    editor.putString(key, value)
//    editor.apply()
//}
//fun getStringValueFromPref(key: String): String? = getSharedPreference().getString(key, "text")