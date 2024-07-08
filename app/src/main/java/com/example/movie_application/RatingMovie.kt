package com.example.movie_application

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RatingMovie: AppCompatActivity() {

    private lateinit var theStarBar : RatingBar
    private lateinit var faceImage : ImageView
    private lateinit var firebaseInstance: FirebaseDatabase
    private lateinit var recyclerviewForRate: RecyclerView
    private lateinit var theButtonForRate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating_movie)

        theStarBar = findViewById(R.id.theStarBar)
        faceImage = findViewById(R.id.faceImage)
        firebaseInstance = FirebaseDatabase.getInstance()
        recyclerviewForRate = findViewById(R.id.recyclerviewForRate)
        theButtonForRate = findViewById(R.id.theButtonForRate)

        theStarBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (rating <= 1) {
                val rateImageOne = R.drawable.rate1
                faceImage.setImageResource(rateImageOne)
            } else if (rating <= 2) {
                val rateImageTwo = R.drawable.rate2
                faceImage.setImageResource(rateImageTwo)
            } else if (rating <= 3) {
                val rateImageThree = R.drawable.rate3
                faceImage.setImageResource(rateImageThree)
            } else if (rating <= 4) {
                val rateImageFour = R.drawable.rate4
                faceImage.setImageResource(rateImageFour)
            } else if (rating <= 5) {
                val rateImageFive = R.drawable.rate5
                faceImage.setImageResource(rateImageFive)
            }

            theButtonForRate.setOnClickListener {
                obtainDataFromFirebase(present = "", rating.toString())
                if (rating <= 1) {
                    Toast.makeText(this, "We apologize for the experience and will continue to improve.", Toast.LENGTH_LONG).show()
                } else if (rating <= 2) {
                    Toast.makeText(this, "We apologize for not being able to meet your expectations.", Toast.LENGTH_LONG).show()
                } else if (rating <= 3) {
                    Toast.makeText(this, "Thank you for your feedback, we will continue to work hard.", Toast.LENGTH_LONG).show()
                } else if (rating <= 4) {
                    Toast.makeText(this, "Thank you for your kind comments.", Toast.LENGTH_LONG).show()
                } else if (rating <= 5) {
                    Toast.makeText(this, "This is fantastic.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    fun obtainDataFromFirebase(present: String, reviewing: String) {
        val Allusion = firebaseInstance.getReference("newItem/$present")
        val firebaseIdentity: FirebaseAuth = FirebaseAuth.getInstance()
        val identifier: String = firebaseIdentity.currentUser!!.uid
        val newSource = Allusion.push()
        val e_message: String = firebaseIdentity.currentUser!!.email!!

        val updateResult = FirebaseHolder(
            email = "Email: $e_message",
            id = "User ID: $identifier",
            rating = "Rating: $reviewing"
        )

        newSource.setValue(updateResult)
        Allusion.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(dataInconsistency: DatabaseError) {
                Toast.makeText(
                    this@RatingMovie,
                    "Database error with: ${dataInconsistency.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
            override fun onDataChange(dataCapture: DataSnapshot) {
                val value = mutableListOf<FirebaseHolder>()
                dataCapture.children.forEach { inpit: DataSnapshot->
                    val currValue: FirebaseHolder? = inpit.getValue(FirebaseHolder::class.java)
                    if (currValue != null && inpit.exists()) {
                        value.add(currValue)
                    }
                }
                recyclerviewForRate.layoutManager = LinearLayoutManager(this@RatingMovie)
                val converter= FirebaseAdapter(value)
                recyclerviewForRate.adapter = converter
            }
        })
    }
}