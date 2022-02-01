package com.example.keovecities

import android.content.Context
import com.example.keovecities.models.RefreshTokenResponseModel
import retrofit2.Callback
import kotlin.properties.Delegates

class Defaults {

    companion object{
        lateinit var TOKEN: String
        var TOKEN_EXPIRE_TIME: Long? = null
        lateinit var REFRESHTOKEN: String
        var ID: Int? = null
        var ISGUEST :Boolean? = null
    }


    fun getSpData(context : Context){
        val pref = context.getSharedPreferences("keoveCities", 0)
        val isGuest = pref.getBoolean("isGuest", true)
        val token = pref.getString("token", "")
        val refreshToken = pref.getString("refreshToken", "")
        val id = pref.getInt("id", 0)
        val tokenExpireTime = pref.getLong("tokenExpireTime", 0)
        TOKEN = "Bearer " + token!!
        REFRESHTOKEN = refreshToken!!
        ID = id
        ISGUEST = isGuest
        TOKEN_EXPIRE_TIME = tokenExpireTime

    }
    fun setSpData(context: Context, token: String, refreshToken: String, id: Int, isGuest: Boolean, timer: Long){
        var tokenBearer = "Bearer " + token
        TOKEN =  tokenBearer
        REFRESHTOKEN = refreshToken
        ID = id
        ISGUEST = isGuest
        TOKEN_EXPIRE_TIME = timer

        val pref = context.getSharedPreferences("keoveCities", 0)
        val editor = pref.edit()
        editor.putString("token",token)
        editor.putString("refreshToken",refreshToken)
        editor.putInt("id",id)
        editor.putBoolean("isGuest",isGuest)
        editor.putLong("tokenExpireTime",timer)
        editor.apply()
    }
}