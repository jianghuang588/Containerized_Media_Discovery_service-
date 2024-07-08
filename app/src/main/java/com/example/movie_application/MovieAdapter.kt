package com.example.movie_application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MovieAdapter(val value: List<Movie>): RecyclerView.Adapter<MovieAdapter.viewHolder>() {

    class viewHolder(detailView: View) : RecyclerView.ViewHolder(detailView) {

        val header : TextView = detailView.findViewById(R.id.header)
        val movieYear: TextView = detailView.findViewById(R.id.movieYear)
        val id: TextView = detailView.findViewById(R.id.id)
        val pictureOfMovie: ImageView = detailView.findViewById(R.id.pictureOfMovie)
        val description: TextView = detailView.findViewById(R.id.email)
        val rate: TextView = detailView.findViewById(R.id.rate)
    }

    override fun onCreateViewHolder(provider: ViewGroup, displatFormat: Int): viewHolder {

        val display: View = LayoutInflater.from(provider.context).inflate(R.layout.activity_card_view, provider, false)
        return viewHolder(display)
    }

    override fun onBindViewHolder(containner: viewHolder, location: Int) {

        val currentValue = value[location]
        containner.header.text = currentValue.item2
        containner.movieYear.text = currentValue.item3
        containner.id.text = currentValue.item4
        containner.description.text = currentValue.item5
        containner.rate.text = currentValue.item6

        Picasso.get()
            .load(currentValue.item1)
            .into(containner.pictureOfMovie)
    }

    override fun getItemCount(): Int {
        return value.size
    }
}