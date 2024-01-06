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

/**
 * Interface defining the operations for the URL shortening repository.
 */
interface UrlShortenerRepository {
    /**
     * Shortens a given long URL and returns the corresponding URL record.
     *
     * @param longURL The long URL to shorten.
     * @param shortURL The desired short URL (optional).
     * @return A URLRecord representing the shortened URL.
     */
    suspend fun shortenURL(longURL: String, shortURL: String): URLRecord

    /**
     * Retrieves the history of shortened URLs as a flow.
     *
     * @return A Flow emitting a list of URLRecords.
     */
    fun getURLHistory(): Flow<List<URLRecord>>
}

/**
 * Implementation of the UrlShortenerRepository using a local database and a network service.
 *
 * @param URLDao The Data Access Object for URL operations in the database.
 * @param UrlShortenerService The network service for URL shortening.
 * @param context The application context.
 */
class URLRepository(
    private val URLDao: URLDao,
    private val UrlShortenerService: UrlShortenerService,
    context: Context
) : UrlShortenerRepository {

    /**
     * Shortens a URL by calling the network service and saves the result in the local database.
     *
     * @param longURL The long URL to shorten.
     * @param shortURL The desired short URL (optional).
     * @return The shortened URL as a URLRecord.
     */
    override suspend fun shortenURL(longURL: String, shortURL: String): URLRecord {
        val resp = UrlShortenerService.shortenURL(longURL, shortURL)
        val urlRecord = URLRecord(resp.short, resp.long, Date.from(Instant.now()))
        URLDao.insert(urlRecord.asDBRecord())
        return urlRecord
    }

    /**
     * Retrieves a list of all URL records stored in the database.
     *
     * @return A Flow emitting a list of URLRecords.
     */
    override fun getURLHistory(): Flow<List<URLRecord>> {
        return URLDao.getAllItems().map { it.asDomainObjects() }
    }
}
