package com.example.keovecities.api

import com.example.keovecities.models.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

     @POST("/guest/auth")
     fun getGuestAuth(): Call<GuestAuthModel>


     @POST("/user/login")
     fun getUserLogin(): Call<UserLoginModel>

     @FormUrlEncoded
     @POST("/user/sign-up")
     fun getUserSignUp(@Field("username") username: String, @Field("password") password : String): Call<UserSignUpModel>

     @GET("/api/City")
     fun getCities(@Header("Authorization") token : String): Call<List<CityModel>>

     @GET("/api/City/{cityId}/districts")
     fun getDistrict(@Path("cityId") cityId: Int): Call<List<DistrictModel>>

}