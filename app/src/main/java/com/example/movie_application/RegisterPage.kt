package com.example.movie_application

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser

class RegisterPage: AppCompatActivity() {
    lateinit var firebaseAccess: FirebaseAuth
    lateinit var userId: EditText
    lateinit var accessCode: EditText
    lateinit var verifyPassword: EditText
    lateinit var returnButton: Button
    lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
        firebaseAccess = FirebaseAuth.getInstance()
        userId = findViewById(R.id.registerUserId)
        accessCode = findViewById(R.id.registerPassword)
        verifyPassword = findViewById(R.id.registerConfirmPassword)
        registerButton = findViewById(R.id.register)
        returnButton = findViewById(R.id.mainPage)
        userId.addTextChangedListener(textEventListener)
        accessCode.addTextChangedListener(textEventListener)
        verifyPassword.addTextChangedListener(textEventListener)
        registerButton.isEnabled = false

        registerButton.setOnClickListener {
            val userId: String = userId.text.toString()
            val accessCode: String = accessCode.text.toString()
            firebaseAccess
                .createUserWithEmailAndPassword(userId, accessCode)
                .addOnCompleteListener { operation: Task<AuthResult> ->
                    if (operation.isSuccessful) {
                        val currClient: FirebaseUser = firebaseAccess.currentUser!!
                        val existingEmail = currClient.email
                        Toast.makeText(
                            this,
                            "Successfully enrolled as $existingEmail",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val outlier: Exception = operation.exception!!
                        when (outlier) {
                            is FirebaseAuthInvalidCredentialsException -> {
                                Toast.makeText(
                                    this,
                                    "Unrecognized credentials",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is FirebaseAuthUserCollisionException -> {
                                Toast.makeText(
                                    this,
                                    "The email is already in the record",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            else -> {
                                Toast.makeText(
                                    this,
                                    "Unable to enroll: $outlier",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        returnButton.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
    private val textEventListener: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val userId: String = userId.text.toString()
            val accessCode: String = accessCode.text.toString()
            val verifyPassword = verifyPassword.text.toString()
            val permit: Boolean = userId.trim().isNotEmpty() && accessCode.trim().isNotEmpty()&&verifyPassword.trim().isNotEmpty()
            registerButton.isEnabled = permit
            if (accessCode != verifyPassword) {
                registerButton.isEnabled = false
            }
        }
        override fun afterTextChanged(s: Editable?) {}
    }
}