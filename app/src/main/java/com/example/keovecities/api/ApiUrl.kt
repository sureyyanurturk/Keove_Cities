package com.example.keovecities.api

import android.content.Context
import android.util.Log
import com.example.keovecities.Defaults
import com.example.keovecities.extension.isValidToken
import com.example.keovecities.models.RefreshTokenModel
import com.example.keovecities.models.RefreshTokenResponseModel
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.internal.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiUrl {
    private val BASE_URL = "https://case.keove.com"

    private lateinit var retrofit: Retrofit


    fun getClient(context: Context): Retrofit {
        var token = Defaults.TOKEN
        if (!isValidToken()){

            refreshToken(context)

        }else return  createRetrofit(token)

        return createRetrofit(token)
    }

    fun getClientWithoutToken(): Retrofit {
        createRetrofit(null)
        return retrofit
    }

    private fun createRetrofit(token:String?): Retrofit {

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.protocols(Util.immutableList(Protocol.HTTP_1_1))

        httpClient.addInterceptor { chain ->
            val request: Request
            if (token != null) {
                 request =
                    chain.request().newBuilder().addHeader("Authorization", token).build()
            }else{
                request =
                    chain.request().newBuilder().build()
            }

            chain.proceed(request)
        }

        val client: OkHttpClient = httpClient.build()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit
    }

    fun refreshToken(context : Context) {

        val requestRefreshBody = RefreshTokenModel(Defaults.REFRESHTOKEN)

        val retrofitToken: Retrofit = ApiUrl().getClientWithoutToken()
        val apiService = retrofitToken.create(ApiService::class.java).also {

            it.getRefreshToken(requestRefreshBody)
                .enqueue(object : Callback<RefreshTokenResponseModel> {
                    override fun onResponse(
                        call: Call<RefreshTokenResponseModel>?,
                        response: retrofit2.Response<RefreshTokenResponseModel>?
                    ) {

                        if (response!!.code() == 200) {
                            Defaults().setSpData(context,
                                response.body().token,
                                response.body().refreshToken,
                                response.body().id,
                                response.body().isGuest,
                                System.currentTimeMillis()
                            )
                        retrofit =  createRetrofit(response.body().token)
                        }

                    }

                    override fun onFailure(call: Call<RefreshTokenResponseModel>?, t: Throwable?) {
                        Log.e("Response", ": " + t.toString())
                    }

                })

        }

    }

}