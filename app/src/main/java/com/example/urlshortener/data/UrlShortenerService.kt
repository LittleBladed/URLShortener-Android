package com.example.urlshortener.data

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UrlShortenerService {

    @GET("addURL.php")
    suspend fun addURL(
        @Query("url") longUrl: String,
        @Query("cu") shortUrl: String?
    ): Response<UrlResponse>
    data class UrlRequest(val long: String, val short: String? = null)
    data class UrlResponse(val message: String, val short: String, val long: String, val receivedRequestedShort: Boolean? = null)
}