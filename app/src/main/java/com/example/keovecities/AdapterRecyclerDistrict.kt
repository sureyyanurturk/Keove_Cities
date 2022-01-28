package com.example.keovecities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.keovecities.models.DistrictModel

class AdapterRecyclerDistrict(private var data: List<DistrictModel>, var context: CityDetailsActivity) : RecyclerView.Adapter<AdapterRecyclerDistrict.MainViewHolder>() {
    var list = data

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var districtText: TextView = itemView.findViewById(R.id.districtText)
        //var districtCard: CardView = itemView.findViewById(R.id.cityCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_district_recycler, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        list[position].name.let {
            holder.districtText.text = it
        }
       /* holder.districtCard.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", list[position].id)
            val intent = Intent(context, CityDetailsActivity::class.java)
            context.startActivity(intent)
        })
*/

    }

    override fun getItemCount(): Int {
        return list.size
    }
}