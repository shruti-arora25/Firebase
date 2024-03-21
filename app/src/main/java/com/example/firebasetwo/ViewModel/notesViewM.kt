package com.example.firebasetwo.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.firebasetwo.Repo.NotesRepo
import com.example.firebasetwo.database.database
import com.example.firebasetwo.model.Notes

    class notesViewM(application: Application) : AndroidViewModel(application) {

    var repository: NotesRepo

    init {

        try {
            val dao = database.getDbInstance(application).MyNotesDao()
            repository = NotesRepo(dao)
        } catch (e: Exception) {
            Log.e("ViewModelInit", "Error initializing ViewModel", e)
            // Handle the error appropriately


            val dao = database.getDbInstance(application).MyNotesDao()
            repository = NotesRepo(dao)
        }


    }

    fun addNotes(notes: Notes) {
        repository.insertNotes(notes)
    }

    fun getNotes(): LiveData<List<Notes>> = repository.getAllNotes()

    fun deleteNotes(id: Int) {

        repository.deleteNotes(id)
    }

    fun updateNotes(notes: Notes){
        repository.updateNote(notes)
    }

}