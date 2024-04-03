package com.example.utibuhealth

data class Order(
val userId: String,
val disease: String,
val prescriptions: List<String>,
val timestamp: Long = System.currentTimeMillis()
)

