package com.example.utibuhealth

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PrescriptionActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("Prescriptions")

        // Get references to UI elements
        val radioGroupDisease: RadioGroup = findViewById(R.id.radioGroupDisease)
        val buttonConfirmDisease: Button = findViewById(R.id.buttonConfirmDisease)

        // Handle confirm button click
        buttonConfirmDisease.setOnClickListener {
            // Get the selected disease
            val selectedDiseaseId = radioGroupDisease.checkedRadioButtonId
            val selectedRadioButton: RadioButton? = findViewById(selectedDiseaseId)
            val selectedDisease = selectedRadioButton?.text.toString()

            // Proceed with prescription selection based on the selected disease
            selectPrescription(selectedDisease)
        }
    }

    private fun selectPrescription(disease: String) {
        val radioGroupPrescription: RadioGroup = findViewById(R.id.radioGroupPrescription)
        val selectedPrescriptionId = radioGroupPrescription.checkedRadioButtonId
        val selectedPrescriptionRadioButton: RadioButton? = findViewById(selectedPrescriptionId)
        val selectedPrescription = selectedPrescriptionRadioButton?.text.toString()

        // Save the selected disease and prescription in the database
        savePrescriptionToDatabase(disease, selectedPrescription)
    }

    private fun savePrescriptionToDatabase(disease: String, prescription: String) {
        // Placeholder code to save prescription information to the database
        // Here, we create a unique key for each prescription entry
        val prescriptionId = databaseReference.push().key

        if (prescriptionId != null) {
            val prescriptionData = hashMapOf(
                "disease" to disease,
                "prescription" to prescription
            )

            databaseReference.child(prescriptionId).setValue(prescriptionData)
                .addOnSuccessListener {
                    // Prescription saved successfully
                    Toast.makeText(this, "Prescription saved successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    // Error occurred while saving prescription
                    Toast.makeText(this, "Failed to save prescription", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
