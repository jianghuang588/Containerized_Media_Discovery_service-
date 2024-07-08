package com.example.movie_application

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        val movie = MovieManager()
        recyclerView = findViewById(R.id.BottomNavigationView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch(Dispatchers.IO) {
            val retriveValue = movie.retrieveMovie()
            val tweets = retriveValue.toList()
            runOnUiThread {
                val converter = MovieAdapter(tweets)
                recyclerView.adapter = converter
            }
        }

        val navigationBar = findViewById<BottomNavigationView>(R.id.navigationBar)
        navigationBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.find -> {
                    val intent = Intent(this, MovieInformation::class.java)
                    startActivity(intent)
                    true
                }
                R.id.baseline_upcoming_24 -> {
                    val intent = Intent(this, UpcomingMovie::class.java)
                    startActivity(intent)
                    true
                }
                R.id.rate -> {
                    val intent = Intent(this, RatingMovie::class.java)
                    startActivity(intent)
                    true
                }
                R.id.personal -> {
                    val intent = Intent(this, PersonalPage::class.java)
                    startActivity(intent)
                    true
                }
                R.id.search -> {
                    val intent = Intent(this, MovieSearch::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}
