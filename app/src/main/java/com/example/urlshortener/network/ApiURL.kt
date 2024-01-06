package com.example.urlshortener.network

import com.example.urlshortener.model.URLRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import java.time.Instant
import java.util.Date

/**
 * Data class representing the response from the URL shortening API.
 *
 * @property message The response message from the API.
 * @property short The shortened URL.
 * @property long The original long URL.
 * @property receivedRequestedShort Indicates whether the requested short URL was received.
 */
@Serializable
data class ApiURLResponse (
    val message: String,
    val short: String,
    val long: String,
    val receivedRequestedShort: Boolean? = null
)

/**
 * Extension function to convert a Flow of [ApiURLResponse] to a Flow of [URLRecord].
 *
 * @return A Flow emitting [URLRecord] objects.
 */
fun Flow<ApiURLResponse>.asDomainObject(): Flow<URLRecord> {
    return this.asDomainObject()
}

/**
 * Extension function to convert an [ApiURLResponse] to a [URLRecord].
 *
 * @return A [URLRecord] object.
 */
fun ApiURLResponse.asDomainObject(): URLRecord{
    return URLRecord(this.short, this.long, Date.from(Instant.now()))
}
