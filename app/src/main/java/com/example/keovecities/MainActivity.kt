package com.example.keovecities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.keovecities.models.CityModel
import com.example.keovecities.api.ApiService
import com.example.keovecities.api.ApiUrl
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


       /* Defaults.USERTOKEN = ""
        val mySPrefs = applicationContext.getSharedPreferences("cinedb", 0)
        val editor = mySPrefs.edit()
        editor.remove("token")
        editor.apply()
        logout*/


        Defaults().TOKEN=""
        val pref = applicationContext.getSharedPreferences("koeveCities", 0)
        val tkn = pref.getString("token", "")
        if (tkn!!.isNotEmpty()) {
            Defaults().TOKEN = "Bearer " + pref.getString("token", "")
            getData(Defaults().TOKEN)
        }
        else{
            getGuestAuth(pref)
            getData(Defaults().TOKEN)
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
    fun getData(token : String) {
        val retrofit: Retrofit = ApiUrl().getClient()
        val apiService = retrofit.create(ApiService::class.java).also {

            it.getCities(token).enqueue( object :Callback<List<CityModel>>{
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

      fun getGuestAuth(pref : SharedPreferences){

        val editor = pref.edit()

            val retrofit: Retrofit = ApiUrl().getClient()
            val apiService = retrofit.create(ApiService::class.java).also {

                it.getGuestAuth().enqueue( object :Callback<GuestAuthModel>{
                    override fun onResponse(
                        call: Call<GuestAuthModel>?,
                        response: Response<GuestAuthModel>?
                    ) {
                        val guestToken = response!!.body().token
                        editor.putString("guestToken", guestToken).apply()
                        Defaults().TOKEN = "Bearer " + guestToken
                        Log.e("token", ": "+ Defaults().TOKEN )
                    }

                    override fun onFailure(call: Call<GuestAuthModel>?, t: Throwable?) {
                        Log.e("Response", ": " + t.toString())
                    }

                })
            }
   }


    fun userImageBtnClick(view: android.view.View) {
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
    }

}