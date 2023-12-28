package com.example.urlshortener.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [dbURLRecord::class], version = 2, exportSchema = false)
abstract class URLDb : RoomDatabase() {

    abstract fun URLDao(): URLDao

    companion object {
        @Volatile
        private var Instance: URLDb? = null

        fun getDatabase(context: Context): URLDb {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, URLDb::class.java, "url_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
