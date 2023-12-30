package com.example.urlshortener.data

import android.content.Context
import com.example.urlshortener.data.database.URLDb
import com.example.urlshortener.network.NetworkConnectionInterceptor
import com.example.urlshortener.network.UrlShortenerService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val urlRepository: URLRepository
}

//container that takes care of dependencies
class DefaultAppContainer(private val context: Context): AppContainer{

    private val networkCheck = NetworkConnectionInterceptor(context)
    private val client = OkHttpClient.Builder()
        .addInterceptor(networkCheck)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://csclub.uwaterloo.ca/~phthakka/1pt/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private val apiService = retrofit.create(UrlShortenerService::class.java)

    private val retrofitService: UrlShortenerService by lazy {
        retrofit.create(UrlShortenerService::class.java)
    }


    override val urlRepository: URLRepository by lazy {
        URLRepository(URLDb.getDatabase(context = context).URLDao(), retrofitService, context)
    }

}