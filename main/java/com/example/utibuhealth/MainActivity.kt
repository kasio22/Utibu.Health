package com.example.utibuhealth

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var welcomeText: TextView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        welcomeText = findViewById(R.id.welcome_text)
        bottomNav = findViewById(R.id.bottom_nav)
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("Orders")


        val username = firebaseAuth.currentUser?.displayName ?: "User"
        welcomeText.text = "Welcome to Utibu Health, $username!"

        bottomNav.setOnNavigationItemSelectedListener() { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Do nothing (already on home)
                    true
                }
                R.id.navigation_prescription -> {
                    startActivity(Intent(this, PrescriptionActivity::class.java))
                    true
                }
                R.id.navigation_profile -> {

                    true
                }
                else -> false
            }
        }
    }
}
