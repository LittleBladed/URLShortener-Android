package com.example.urlshortener.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.urlshortener.model.URLRecord
import java.util.Date

@Entity(tableName="urlRecords")
@TypeConverters(DateConverter::class)
data class dbURLRecord(
    @PrimaryKey
    val shortURL: String = "",
    val longURL: String = "",
    val date: Date
)

fun dbURLRecord.asDomainObject(): URLRecord {
    return URLRecord(
        this.shortURL,
        this.longURL,
        this.date
    )
}

fun URLRecord.asDBRecord(): dbURLRecord {
    return dbURLRecord(
        shortURL = this.shortURL,
        longURL = this.longURL,
        date = this.date
    )
}

fun List<dbURLRecord>.asDomainObjects(): List<URLRecord> {
    var records = this.map {
        URLRecord(it.shortURL, it.longURL, it.date)
    }
    return records
}