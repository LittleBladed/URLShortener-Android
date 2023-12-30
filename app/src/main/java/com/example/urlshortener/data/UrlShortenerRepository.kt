package com.example.urlshortener.data

import android.content.Context
import com.example.urlshortener.data.database.URLDao
import com.example.urlshortener.data.database.asDBRecord
import com.example.urlshortener.data.database.asDomainObjects
import com.example.urlshortener.model.URLRecord
import com.example.urlshortener.network.UrlShortenerService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.util.Date


interface UrlShortenerRepository {
    suspend fun shortenURL(longURL: String, shortURL: String): URLRecord
    fun getURLHistory(): Flow<List<URLRecord>>
}

class URLRepository(private val URLDao: URLDao, private val UrlShortenerService: UrlShortenerService, context: Context) : UrlShortenerRepository {
    override suspend fun shortenURL(longURL: String, shortURL: String): URLRecord {
        var resp = UrlShortenerService.shortenURL(longURL, shortURL)
        var urlRecord = URLRecord(resp.short, resp.long, Date.from(Instant.now()))
        URLDao.insert(urlRecord.asDBRecord())
        return urlRecord
    }




    override fun getURLHistory(): Flow<List<URLRecord>> {
        return URLDao.getAllItems().map {
            it.asDomainObjects()
        }
    }

}