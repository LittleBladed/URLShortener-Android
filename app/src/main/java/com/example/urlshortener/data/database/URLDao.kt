package com.example.urlshortener.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface URLDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: dbURLRecord)

    @Update
    suspend fun update(item: dbURLRecord)

    @Delete
    suspend fun delete(item: dbURLRecord)

    @Query("SELECT * from urlRecords WHERE shortURL = :shortURL")
    fun getItem(shortURL: String): Flow<dbURLRecord>

    @Query("SELECT * from urlRecords ORDER BY shortURL ASC")
    fun getAllItems(): Flow<List<dbURLRecord>>
}