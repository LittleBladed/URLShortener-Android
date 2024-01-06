package com.example.urlshortener.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for URL operations in the Room database.
 * Defines the methods for accessing the database.
 */
@Dao
interface URLDao {

    /**
     * Inserts a new URL record into the database.
     * If the URL already exists, it replaces the old record.
     *
     * @param item The dbURLRecord object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: dbURLRecord)

    /**
     * Updates an existing URL record in the database.
     *
     * @param item The dbURLRecord object to be updated.
     */
    @Update
    suspend fun update(item: dbURLRecord)

    /**
     * Deletes a URL record from the database.
     *
     * @param item The dbURLRecord object to be deleted.
     */
    @Delete
    suspend fun delete(item: dbURLRecord)

    /**
     * Retrieves a URL record by its short URL.
     *
     * @param shortURL The short URL string of the record.
     * @return A Flow emitting the dbURLRecord object.
     */
    @Query("SELECT * from urlRecords WHERE shortURL = :shortURL")
    fun getItem(shortURL: String): Flow<dbURLRecord>

    /**
     * Retrieves all URL records in the database, ordered by the short URL.
     *
     * @return A Flow emitting a list of dbURLRecord objects.
     */
    @Query("SELECT * from urlRecords ORDER BY shortURL ASC")
    fun getAllItems(): Flow<List<dbURLRecord>>
}
