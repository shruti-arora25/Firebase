package com.example.firebasetwo.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.firebasetwo.R

class ToDoNotesActivity : AppCompatActivity() {
    private lateinit var NavC:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_notes)

    }

    override fun onSupportNavigateUp(): Boolean {
        NavC=findNavController(R.id.Container)
        return super.onSupportNavigateUp()
    }
}