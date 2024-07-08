package com.example.movie_application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FirebaseAdapter(val value: List<FirebaseHolder>): RecyclerView.Adapter<FirebaseAdapter.viewHolder>() {
    class viewHolder(detailView: View) : RecyclerView.ViewHolder(detailView) {

        val id: TextView = detailView.findViewById(R.id.id)
        val email: TextView = detailView.findViewById(R.id.email)
        val rating: TextView = detailView.findViewById(R.id.rate)
    }

    override fun onCreateViewHolder(mentor: ViewGroup, displayType: Int): viewHolder {

        val perception: View = LayoutInflater.from(mentor.context).inflate(R.layout.card_view_for_firebase, mentor, false)
        return viewHolder(perception)
    }

    override fun onBindViewHolder(guard: viewHolder, spot: Int) {

        val spot = value[spot]
        guard.id.text = spot.id
        guard.email.text = spot.email
        guard.rating.text = spot.rating
    }

    override fun getItemCount(): Int {
        return value.size
    }
}