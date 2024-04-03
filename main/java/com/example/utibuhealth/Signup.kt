package com.example.utibuhealth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.utibuhealth.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        binding.SignupButton.setOnClickListener {
            val signupEmail = binding.SignUpEmail.text.toString()
            val signupUsername = binding.SignUpUsername.text.toString()
            val signupPassword = binding.SignUpPassword.text.toString()

            if (signupEmail.isNotEmpty() && signupUsername.isNotEmpty() && signupPassword.isNotEmpty()) {
                signupUser(signupEmail, signupUsername, signupPassword)
            } else {
                Toast.makeText(this@Signup, "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }

        binding.LoginRedirect.setOnClickListener {
            startActivity(Intent(this@Signup, Login::class.java))
            finish()
        }
    }

    private fun signupUser(email: String, username: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Send email verification
                    firebaseAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener { emailVerificationTask ->
                        if (emailVerificationTask.isSuccessful) {
                            // Save user data to Firebase Realtime Database
                            val userId = firebaseAuth.currentUser?.uid
                            val userData = UserData(userId, email, username)
                            userId?.let {
                                databaseReference.child(it).setValue(userData)
                            }
                            Toast.makeText(this@Signup, "Sign up successful. Verification email sent.", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@Signup, Login::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@Signup, "Failed to send verification email: ${emailVerificationTask.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@Signup, "Sign up failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
