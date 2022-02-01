package com.example.keovecities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.keovecities.api.ApiService
import com.example.keovecities.api.ApiUrl
import com.example.keovecities.models.CityModel
import com.example.keovecities.models.RefreshTokenModel
import com.example.keovecities.models.RefreshTokenResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class AdapterRecyclerCities(private var data: List<CityModel>,var context: MainActivity) : RecyclerView.Adapter<AdapterRecyclerCities.MainViewHolder>(){
    var list = data

    class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var citiesName: TextView = itemView.findViewById(R.id.cityText)
        var cityCard: CardView = itemView.findViewById(R.id.cityCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cities_recycler, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        list[position].name.let {
            holder.citiesName.text = it
        }
        holder.cityCard.setOnClickListener(View.OnClickListener {

            if(!Defaults.ISGUEST!!){

                val bundle= Bundle()
                bundle.putInt("cityId",list[position].id)
                val intent = Intent(context, CityDetailsActivity::class.java)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }
            else{
                val intent = Intent(context, LogInActivity::class.java)
                context.startActivity(intent)
            }

        })


    }

    override fun getItemCount(): Int {
        return list.size
    }
  /*  fun refreshToken(){
        val requestRefreshBody = RefreshTokenModel(Defaults.REFRESHTOKEN)

        val retrofit: Retrofit = ApiUrl().getClient()
        val apiService = retrofit.create(ApiService::class.java).also {

            it.getRefreshToken(requestRefreshBody)
                .enqueue(object : Callback<RefreshTokenResponseModel> {
                    override fun onResponse(
                        call: Call<RefreshTokenResponseModel>?,
                        response: Response<RefreshTokenResponseModel>?
                    ) {

                        if (response!!.code() == 200) {
                            Defaults().setSpData(
                                context,
                                response.body().token,
                                response.body().refreshToken,
                                response.body().id,
                                response.body().isGuest,
                                System.currentTimeMillis()
                            )
                        }
                    }

                    override fun onFailure(call: Call<RefreshTokenResponseModel>?, t: Throwable?) {
                        Log.e("Response", ": " + t.toString())
                    }

                })

        }

    }*/
}