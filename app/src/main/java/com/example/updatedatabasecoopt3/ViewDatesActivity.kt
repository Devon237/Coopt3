package com.example.updatedatabasecoopt3

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewDatesActivity : AppCompatActivity() {

    private lateinit var dateListView: ListView
    private lateinit var database: FirebaseDatabase
    private lateinit var clearButton: Button
    private lateinit var btnGoBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_dates)

        dateListView = findViewById(R.id.dateListView)
        btnGoBack = findViewById(R.id.btnGoBack)
        clearButton = findViewById(R.id.clearButton)
        database = FirebaseDatabase.getInstance()

        val dateReference = database.getReference("dates")
        dateReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dates = mutableListOf<String>()
                for (dateSnapshot in snapshot.children) {
                    val date = dateSnapshot.getValue(String::class.java)
                    date?.let { dates.add(it) }
                }

                val adapter = ArrayAdapter(this@ViewDatesActivity, android.R.layout.simple_list_item_1, dates)
                dateListView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        clearButton.setOnClickListener {
            clearDates()
        }

        btnGoBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun clearDates() {
        val dateReference = database.getReference("dates")
        dateReference.removeValue()
        Toast.makeText(this, "Dates Deleted Successfully", Toast.LENGTH_SHORT).show()
    }
}