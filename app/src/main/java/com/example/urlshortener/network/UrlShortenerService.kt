package com.example.urlshortener.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.Exception

/**
 * Interface for the URL shortener service using Retrofit.
 * Defines the HTTP operations for interacting with a URL shortening API.
 */
interface UrlShortenerService {

    /**
     * Sends a request to the URL shortening API to shorten a given URL.
     *
     * @param longUrl The original long URL to be shortened.
     * @param shortUrl An optional custom short URL.
     * @return The response from the API containing the shortened URL or error details.
     */
    @GET("addURL.php")
    suspend fun shortenURL(
        @Query("url") longUrl: String,
        @Query("cu") shortUrl: String?
    ): ApiURLResponse
}

/**
 * Extension function to create a Kotlin Flow for fetching the shortened URL.
 *
 * @param longUrl The original long URL to be shortened.
 * @param shortUrl An optional custom short URL.
 * @return A Flow emitting the API response.
 */
fun UrlShortenerService.getURLasFlow(longUrl: String, shortUrl: String): Flow<ApiURLResponse> = flow {
    try {
        emit(shortenURL(longUrl, shortUrl))
    } catch (e: Exception) {
        Log.e("API", "getURLasFlow: " + e.stackTraceToString())
    }
}
