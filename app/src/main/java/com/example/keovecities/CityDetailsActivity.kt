package com.example.keovecities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.keovecities.models.DistrictModel
import com.example.keovecities.api.ApiService
import com.example.keovecities.api.ApiUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

abstract class CityDetailsActivity : AppCompatActivity() {

    lateinit var recyclerViewDistrict : RecyclerView
    lateinit var userImage : ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_details)

        val cityId = intent.extras!!.getInt("cityId")



       /* recyclerViewDistrict = findViewById(R.id.recyclerViewDistrict)
        recyclerViewDistrict.layoutManager = LinearLayoutManager(this)


        val retrofit: Retrofit = ApiUrl().getClient()
        val apiService = retrofit.create(ApiService::class.java).also {

            it.getDistrict(cityId).enqueue( object : Callback<List<DistrictModel>> {
                override fun onResponse(
                    call: Call<List<DistrictModel>>?,
                    response: Response<List<DistrictModel>>?
                ) {
                    recyclerViewDistrict.adapter = AdapterRecyclerDistrict(response!!.body(),this@CityDetailsActivity)
                }

                override fun onFailure(call: Call<List<DistrictModel>>?, t: Throwable?) {
                    Log.e("Response", ": " + t.toString())
                }

            })
        }
*/
    }

}