package com.name.a15androidkotlinmaps.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.name.a15androidkotlinmaps.databinding.RecyclerRowBinding
import com.name.a15androidkotlinmaps.model.Place
import com.name.a15androidkotlinmaps.view.MapsActivity

class PlaceAdapter (val placeList : List<Place>) :RecyclerView.Adapter<PlaceAdapter.PlaceHolder> () {

    class PlaceHolder (val recylerRowBinding: RecyclerRowBinding) : RecyclerView.ViewHolder (recylerRowBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val recylerRowBinding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceHolder(recylerRowBinding)
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        holder.recylerRowBinding.recyclerViewTextView.text = placeList.get(position).name
        holder.itemView.setOnClickListener {
            val intent=Intent(holder.itemView.context,MapsActivity::class.java)
            intent.putExtra("selectedPlace",placeList.get(position))
            intent.putExtra("info","old")

            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return placeList.size
    }

}