package com.example.urlshortener.network

import com.example.urlshortener.model.URLRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import java.time.Instant
import java.util.Date


@Serializable
data class ApiURLResponse (
    val message: String,
    val short: String,
    val long: String,
    val receivedRequestedShort: Boolean? = null
)

fun Flow<ApiURLResponse>.asDomainObject(): Flow<URLRecord> {
    return this.asDomainObject()
}

fun ApiURLResponse.asDomainObject(): URLRecord{
    return URLRecord(this.short, this.long, Date.from(Instant.now()))
}
