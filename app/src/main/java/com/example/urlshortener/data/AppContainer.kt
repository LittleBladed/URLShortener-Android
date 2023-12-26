package com.example.urlshortener.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
}

//container that takes care of dependencies
class DefaultAppContainer(): AppContainer{


}