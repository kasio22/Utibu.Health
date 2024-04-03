package com.example.utibuhealth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.utibuhealth.databinding.ActivityLogin1Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLogin1Binding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.LoginButton.setOnClickListener {
            val loginUsername = binding.LoginUsername.text.toString()
            val loginPassword = binding.LoginPassword.text.toString()

            if (loginUsername.isNotEmpty() && loginPassword.isNotEmpty()) {
                loginUser(loginUsername, loginPassword)
            } else {
                Toast.makeText(this@Login, "Username and password are required", Toast.LENGTH_SHORT).show()
            }
        }

        binding.SignUpRedirect.setOnClickListener {
            startActivity(Intent(this@Login, Signup::class.java))
            finish()
        }
    }

    private fun loginUser(email: String, password: String){
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Check if the user's email is verified
                        val user = firebaseAuth.currentUser
                        if (user != null && user.isEmailVerified) {
                            // Sign in success and email is verified, update UI with the signed-in user's information
                            Toast.makeText(this@Login, "Login Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@Login, MainActivity::class.java))
                            finish()
                        } else {
                            // Email is not verified, prompt user to verify their email
                            Toast.makeText(this@Login, "Please verify your email address first", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        val errorMessage = task.exception?.message ?: "Unknown error"
                        Toast.makeText(this@Login, "Authentication failed: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

