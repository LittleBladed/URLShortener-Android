package com.example.urlshortener.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.Exception

interface UrlShortenerService {

    @GET("addURL.php")
    suspend fun shortenURL(
        @Query("url") longUrl: String,
        @Query("cu") shortUrl: String?
    ): ApiURLResponse
}

fun UrlShortenerService.getURLasFlow(longUrl: String, shortUrl: String): Flow<ApiURLResponse> = flow {
    try {
        emit(shortenURL(longUrl, shortUrl))
    }
    catch (e: Exception) {
        Log.e("API", "getURLasFlow: "+e.stackTraceToString(), )

    }
}