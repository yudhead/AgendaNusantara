package com.agendanusantara.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [task::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): taskdao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "agenda_db"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE!!
        }
    }
}