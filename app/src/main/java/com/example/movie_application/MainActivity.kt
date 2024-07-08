package com.example.movie_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity: AppCompatActivity() {
    lateinit var firebaseAccess: FirebaseAuth
    lateinit var userId: EditText
    lateinit var accessCode: EditText
    lateinit var loginButton: Button
    lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAccess = FirebaseAuth.getInstance()
        userId = findViewById(R.id.userId)
        accessCode = findViewById(R.id.accessCode)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        userId.addTextChangedListener(textEventListener)
        accessCode.addTextChangedListener(textEventListener)
        loginButton.isEnabled = false

        loginButton.setOnClickListener {

            val userId: String = userId.text.toString()
            val accessCode: String = accessCode.text.toString()
            firebaseAccess
                .signInWithEmailAndPassword(userId, accessCode)
                .addOnCompleteListener { operation: Task<AuthResult> ->
                    if (operation.isSuccessful) {
                        val activeUser: FirebaseUser = firebaseAccess.currentUser!!
                        val presentEmail = activeUser.email
                        Toast.makeText(
                            this,
                            "Authenticates as $presentEmail",
                            Toast.LENGTH_LONG
                        ).show()
                        val intent: Intent = Intent(this, MovieActivity::class.java)
                        startActivity(intent)
                    } else {
                        val issue = operation.exception!!
                        Toast.makeText(
                            this,
                            "Unable to login in $issue",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        registerButton.setOnClickListener {
            val intent: Intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
    }

    private val textEventListener: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val userId: String = userId.text.toString()
            val accessCode: String = accessCode.text.toString()
            val permit: Boolean = userId.trim().isNotEmpty() && accessCode.trim().isNotEmpty()&& accessCode.length > 5
            loginButton.isEnabled = permit

        }
        override fun afterTextChanged(s: Editable?) {}
    }

}

