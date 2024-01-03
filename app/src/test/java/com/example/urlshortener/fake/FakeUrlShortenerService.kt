package com.example.urlshortener.fake

import com.example.urlshortener.network.ApiURLResponse
import com.example.urlshortener.network.UrlShortenerService

class FakeUrlShortenerService : UrlShortenerService{
    override suspend fun shortenURL(longUrl: String, shortUrl: String?): ApiURLResponse {
        return ApiURLResponse("done", shortUrl.toString(), longUrl, true)
    }
}