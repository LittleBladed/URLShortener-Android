package com.example.urlshortener.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.urlshortener.model.URLRecord
import java.util.Date

/**
 * Entity representing a URL record in the database.
 *
 * @property shortURL The primary key, representing the shortened URL.
 * @property longURL The original long URL.
 * @property date The date when the URL was shortened.
 */
@Entity(tableName="urlRecords")
@TypeConverters(DateConverter::class)
data class dbURLRecord(
    @PrimaryKey
    val shortURL: String = "",
    val longURL: String = "",
    val date: Date
)

/**
 * Converts a database URL record to a domain model URL record.
 *
 * @return A URLRecord model object.
 */
fun dbURLRecord.asDomainObject(): URLRecord {
    return URLRecord(
        this.shortURL,
        this.longURL,
        this.date
    )
}

/**
 * Converts a domain model URL record to a database URL record.
 *
 * @return A dbURLRecord object.
 */
fun URLRecord.asDBRecord(): dbURLRecord {
    return dbURLRecord(
        shortURL = this.shortURL,
        longURL = this.longURL,
        date = this.date
    )
}

/**
 * Converts a list of database URL records to a list of domain model URL records.
 *
 * @return A list of URLRecord model objects.
 */
fun List<dbURLRecord>.asDomainObjects(): List<URLRecord> {
    return this.map {
        URLRecord(it.shortURL, it.longURL, it.date)
    }
}
