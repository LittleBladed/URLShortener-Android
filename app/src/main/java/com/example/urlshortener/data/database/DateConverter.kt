package com.example.urlshortener.data.database

import androidx.room.TypeConverter
import java.util.Date

/**
 * A converter class to handle conversion between Date objects and database timestamps.
 * This class is used by Room to convert Date objects to and from Long timestamps
 * when reading from or writing to the database.
 */
class DateConverter {

    /**
     * Converts a timestamp (Long) to a Date object.
     *
     * @param timestamp The Long timestamp to be converted.
     * @return A Date object corresponding to the timestamp, or null if the timestamp is null.
     */
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    /**
     * Converts a Date object to a Long timestamp.
     *
     * @param date The Date object to be converted.
     * @return A Long timestamp corresponding to the Date, or null if the Date is null.
     */
    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}
