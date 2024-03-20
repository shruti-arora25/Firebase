package com.example.firebasetwo.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.firebasetwo.model.Notes

@Dao
interface Dao {
    @Query("Select * from Notes")
    fun getNotes(): LiveData<List<Notes>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: Notes)

    @Query("Delete From notes where id=:idd")
    fun deleteNote(idd: Int)

    @Update
    fun updateNotes(notes: Notes)

}