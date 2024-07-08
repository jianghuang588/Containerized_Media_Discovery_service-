package com.example.movie_application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieInformation: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        val movie = MovieManager()
        recyclerView = findViewById(R.id.newRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        GlobalScope.launch(Dispatchers.IO) {
            val retriveValue = movie.retrievePopularMovie()
            val value = retriveValue.toList()
            runOnUiThread {
                val converter = NewAdapter(value)
                recyclerView.adapter = converter
            }
        }
    }
}