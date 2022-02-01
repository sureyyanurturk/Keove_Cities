package com.example.keovecities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.keovecities.models.CityModel
import com.example.keovecities.api.ApiService
import com.example.keovecities.api.ApiUrl
import com.example.keovecities.extension.isValidToken
import com.example.keovecities.models.GuestAuthModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    lateinit var spinner : Spinner
    lateinit var recyclerViewCities : RecyclerView
    lateinit var userImage : ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (!Defaults.ISGUEST!!) {
             getData()
        }
        else{
            getGuestAuth()
            getData()
        }

        spinner= findViewById(R.id.spinner)
        userImage= findViewById(R.id.userImage)


        val names = resources.getStringArray(R.array.names)
        val adapterSpinner = ArrayAdapter(this, R.layout.spinner_item, names)
        adapterSpinner.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner.adapter = adapterSpinner


        recyclerViewCities = findViewById(R.id.recyclerViewCities)
        recyclerViewCities.layoutManager = LinearLayoutManager(this)


    }
    fun getData() {

            val retrofit: Retrofit = ApiUrl().getClient(this)
            val apiService = retrofit.create(ApiService::class.java).also {

                it.getCities(Defaults.TOKEN).enqueue( object :Callback<List<CityModel>>{
                    override fun onResponse(
                        call: Call<List<CityModel>>?,
                        response: Response<List<CityModel>>?
                    ) {
                        recyclerViewCities.adapter = AdapterRecyclerCities(response!!.body(),this@MainActivity)
                    }

                    override fun onFailure(call: Call<List<CityModel>>?, t: Throwable?) {
                        Log.e("Response", ": " + t.toString())
                    }

                })
            }
        }

    fun getGuestAuth(){

            val retrofit: Retrofit = ApiUrl().getClient(this)
            val apiService = retrofit.create(ApiService::class.java).also {

                it.getGuestAuth().enqueue( object :Callback<GuestAuthModel>{
                    override fun onResponse(
                        call: Call<GuestAuthModel>?,
                        response: Response<GuestAuthModel>?
                    ) {
                        Defaults().setSpData(this@MainActivity, response!!.body().token, response.body().refreshToken, response.body().id, response.body().isGuest, System.currentTimeMillis()
                        )
                    }

                    override fun onFailure(call: Call<GuestAuthModel>?, t: Throwable?) {
                        Log.e("Response", ": " + t.toString())
                    }

                })
            }


        }


    fun userImageBtnClick(view: android.view.View) {
        if(Defaults.ISGUEST!!){
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }else{
             val pref = applicationContext.getSharedPreferences("keoveCities", 0)
             val editor = pref.edit()
             editor.remove("token")
             editor.remove("refreshToken")
             editor.remove("id")
             editor.remove("isGuest")
             Defaults.ID =0
             Defaults.ISGUEST= true
             Defaults.TOKEN= ""
             Defaults.REFRESHTOKEN=""
             editor.apply()
             Toast.makeText(this@MainActivity, "Log out.", Toast.LENGTH_SHORT).show()

        }

    }


}