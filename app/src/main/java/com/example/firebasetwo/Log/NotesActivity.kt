package com.example.firebasetwo.Log

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebasetwo.databinding.ActivityNotesBinding

class NotesActivity : AppCompatActivity() {

    private lateinit var bind:ActivityNotesBinding
    private lateinit var sharedPref:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind=ActivityNotesBinding.inflate(layoutInflater)
        setContentView(bind.root)


        sharedPref=getSharedPreferences("Notes Data",Context.MODE_PRIVATE)

        bind.saveNote.setOnClickListener {

            val notes = bind.noteWrite.text.toString()

            if (notes.isNotEmpty()) {


                val sharedEdit = sharedPref.edit()


                sharedEdit.putString("Note", notes)
                sharedEdit.apply()

                Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()

                bind.noteWrite.text.clear()

            } else {
                Toast.makeText(this, "Enter Note first", Toast.LENGTH_SHORT).show()
            }
        }


            bind.displayNoteBtn.setOnClickListener {

                val storedN=sharedPref.getString("Note","")
                bind.displayNoteView.text="$storedN"

            }



    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

}


