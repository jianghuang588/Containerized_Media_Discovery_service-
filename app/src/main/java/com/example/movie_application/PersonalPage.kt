package com.example.movie_application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class PersonalPage: AppCompatActivity() {

    lateinit var loginOutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)

        loginOutButton = findViewById(R.id.loginOutButton)
        val username = FirebaseAuth.getInstance().currentUser

        if (username != null) {
            val userEmail = username.email
            val id = username.uid
            findViewById<TextView>(R.id.usernameText).text = "User Email:$userEmail"
            findViewById<TextView>(R.id.idText).text = "Id:$id"
        }

        loginOutButton.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}