package com.example.urlshortener.fake

import com.example.urlshortener.data.URLRepository
import com.example.urlshortener.data.UrlShortenerRepository
import com.example.urlshortener.data.database.asDomainObjects
import com.example.urlshortener.model.URLRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.Instant
import java.util.Date

class FakeUrlShortenerRepository: UrlShortenerRepository {
    override suspend fun shortenURL(longURL: String, shortURL: String): URLRecord {
        return URLRecord(shortURL, longURL, Date.from(Instant.now()))
    }

    override fun getURLHistory(): Flow<List<URLRecord>> {
        return flowOf(FakeDataSource.records.asDomainObjects())
    }

}