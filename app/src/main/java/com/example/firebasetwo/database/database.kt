package com.example.firebasetwo.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.firebasetwo.dao.Dao
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

abstract class database : RoomDatabase() {

    abstract fun MyNotesDao(): Dao

    companion object {
        @Volatile
        var INSTANCE: database? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDbInstance(context: Context): database {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val roomDatabaseInstance =
                    Room.databaseBuilder(context, database::class.java, "Notes").build()

                INSTANCE = roomDatabaseInstance
                return return roomDatabaseInstance
            }

        }
    }
}