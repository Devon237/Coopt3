package com.example.updatedatabasecoopt3

import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var dateReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase
        database = FirebaseDatabase.getInstance()
        dateReference = database.getReference("dates")
    }

    fun onSendButtonClick(view: android.view.View) {
        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val selectedDate = "${datePicker.year}-${datePicker.month + 1}-${datePicker.dayOfMonth}"

        // Push the date to the database
        val dateId = dateReference.push().key
        dateId?.let {
            dateReference.child(it).setValue(selectedDate)
            Toast.makeText(this, "Date sent successfully", Toast.LENGTH_SHORT).show()

            // Navigate to ViewDatesActivity
            val intent = Intent(this, ViewDatesActivity::class.java)
            startActivity(intent)
        }
    }

    fun onViewDatesButtonClick(view: android.view.View) {
        // Handle the click of the "View Dates" button
        val intent = Intent(this, ViewDatesActivity::class.java)
        startActivity(intent)
    }
}