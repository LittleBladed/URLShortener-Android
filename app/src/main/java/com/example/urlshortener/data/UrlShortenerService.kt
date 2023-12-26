package com.example.urlshortener.data

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Query

interface UrlShortenerService {

    @POST("addURL")
    suspend fun addURL(
        @Query("long") longUrl: String,
        @Query("short") shortUrl: String?
    ): Response<UrlResponse>
    data class UrlRequest(val long: String, val short: String? = null)
    data class UrlResponse(val message: String, val short: String, val long: String, val receivedRequestedShort: Boolean? = null)
}