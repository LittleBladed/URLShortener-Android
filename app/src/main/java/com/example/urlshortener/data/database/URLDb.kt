package com.example.urlshortener.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Room database for storing URL records.
 *
 * @property entities The list of entities included in the database. In this case, only [dbURLRecord].
 * @property version The version number of the database. Should be incremented with each schema change.
 * @property exportSchema A flag to control the export of the database schema. False, as we are not exporting the schema here.
 */
@Database(entities = [dbURLRecord::class], version = 1, exportSchema = false)
abstract class URLDb : RoomDatabase() {

    /**
     * Abstract method to provide DAO (Data Access Object) for URL operations.
     *
     * @return An instance of [URLDao] to interact with the database.
     */
    abstract fun URLDao(): URLDao

    companion object {
        @Volatile
        private var Instance: URLDb? = null

        /**
         * Gets the singleton instance of [URLDb] database.
         *
         * @param context The context in which the database is created.
         * @return The singleton instance of [URLDb].
         */
        fun getDatabase(context: Context): URLDb {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, URLDb::class.java, "url_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
