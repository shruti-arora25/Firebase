package com.example.firebasetwo.Repo

import androidx.lifecycle.LiveData
import com.example.firebasetwo.dao.Dao
import com.example.firebasetwo.model.Notes

class NotesRepo(val dataAccessObject:Dao) {

    fun getAllNotes():LiveData<List<Notes>>{
        return dataAccessObject.getNotes()
    }



}