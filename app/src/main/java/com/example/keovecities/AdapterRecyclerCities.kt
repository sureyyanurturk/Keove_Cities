package com.example.keovecities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.keovecities.models.CityModel

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

            if(Defaults().ISGUEST == true){
                val intent = Intent(context, LogInActivity::class.java)
                context.startActivity(intent)
            }
            else{
                val bundle= Bundle()
                bundle.putInt("cityId",list[position].id)
                val intent = Intent(context, CityDetailsActivity::class.java)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }

        })


    }

    override fun getItemCount(): Int {
        return list.size
    }
}