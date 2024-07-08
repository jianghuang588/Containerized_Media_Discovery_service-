package com.example.movie_application

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieSearch: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var movieSearchText: EditText
    lateinit var movieSearchButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)

        val movie = MovieManager()
        recyclerView = findViewById(R.id.recyclerview)
        movieSearchText = findViewById(R.id.inputForSearchMovie)
        movieSearchButton = findViewById(R.id.movieSeachButton)
        recyclerView.layoutManager = LinearLayoutManager(this)

        movieSearchButton.setOnClickListener {
            if (movieSearchText.text.isNotEmpty()) {
                GlobalScope.launch(Dispatchers.IO) {
                    val value = movie.retrieveSearchMovie(
                        searchText = movieSearchText.text.toString()
                    )
                    val currValue = value.toList()
                    runOnUiThread {
                        val converter = UpcomingAdapter(currValue)
                        recyclerView.adapter = converter
                    }
                }
            }

        }

    }
}