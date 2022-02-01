package com.example.keovecities.api

import com.example.keovecities.models.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

     @POST("/guest/auth")
     fun getGuestAuth(): Call<GuestAuthModel>

     @POST("/refresh-token")
     fun getRefreshToken(@Body requestRefreshBody : RefreshTokenModel): Call<RefreshTokenResponseModel>

     @POST("/user/login")
     fun getUserLogin(@Header("Authorization") token : String,@Body requestBody : UserLoginModel ): Call<UserLoginResponseModel>

     @POST("/user/sign-up")
     fun getUserSignUp(@Header("Authorization") token : String,@Body requestBody : UserSignUpModel): Call<UserSignUpResponseModel>

     @GET("/api/City")
     fun getCities(@Header("Authorization") token : String): Call<List<CityModel>>

     @GET("/api/City/{cityId}/districts")
     fun getDistrict(@Header("Authorization") token : String, @Path("cityId") cityId: Int): Call<List<DistrictModel>>

}