package com.example.movie_application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpcomingMovie: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_movie)

        val movie = MovieManager()
        recyclerView = findViewById(R.id.newView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        GlobalScope.launch(Dispatchers.IO) {
            val value = movie.retrieveUpcomingMovie()
            val currValue = value.toList()
            runOnUiThread {
                val converter = UpcomingAdapter(currValue)
                recyclerView.adapter = converter
            }
        }
    }
}