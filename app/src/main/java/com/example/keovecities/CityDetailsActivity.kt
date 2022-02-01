package com.example.keovecities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.keovecities.models.DistrictModel
import com.example.keovecities.api.ApiService
import com.example.keovecities.api.ApiUrl
import com.example.keovecities.extension.isValidToken
import com.example.keovecities.models.RefreshTokenModel
import com.example.keovecities.models.RefreshTokenResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

 class CityDetailsActivity : AppCompatActivity() {

    lateinit var recyclerViewDistrict : RecyclerView
    lateinit var userImage : ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_details)

        val cityId = intent.extras!!.getInt("cityId")



        recyclerViewDistrict = findViewById(R.id.recyclerViewDistrict)
        recyclerViewDistrict.layoutManager = LinearLayoutManager(this)


            val retrofit: Retrofit = ApiUrl().getClient(this)
            val apiService = retrofit.create(ApiService::class.java).also {


                it.getDistrict(Defaults.TOKEN, cityId)
                    .enqueue(object : Callback<List<DistrictModel>> {
                        override fun onResponse(
                            call: Call<List<DistrictModel>>?,
                            response: Response<List<DistrictModel>>?
                        ) {
                            if(response?.code() == 200){
                                recyclerViewDistrict.adapter = AdapterRecyclerDistrict(response!!.body(), this@CityDetailsActivity)
                            }
                            else Toast.makeText(this@CityDetailsActivity, "401", Toast.LENGTH_LONG).show()
                        }

                        override fun onFailure(call: Call<List<DistrictModel>>?, t: Throwable?) {
                            Log.e("Response", ": " + t.toString())
                        }

                    })
            }
        }

     fun backBtn(view: android.view.View) { finish() }

    }



