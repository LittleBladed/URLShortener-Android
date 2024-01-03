package com.example.urlshortener

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.urlshortener.data.database.URLDao
import com.example.urlshortener.data.database.URLDb
import com.example.urlshortener.data.database.asDBRecord
import com.example.urlshortener.data.database.asDomainObject
import com.example.urlshortener.model.URLRecord
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.Instant
import java.util.Date

@RunWith(AndroidJUnit4::class)
class URLDaoTest {
    private lateinit var UrlDao: URLDao
    private lateinit var urlDb: URLDb

    private var url1 = URLRecord("hehehe", "https://google.com", Date.from(Instant.now()))
    private var url2 = URLRecord("hahaha", "https://youtube.com", Date.from(Instant.now()))

    // unility functions
    private suspend fun addOneURLToDB() {
        UrlDao.insert(url1.asDBRecord())
    }

    private suspend fun addTwoURLsToDB() {
        UrlDao.insert(url1.asDBRecord())
        UrlDao.insert(url2.asDBRecord())
    }

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        urlDb = Room.inMemoryDatabaseBuilder(context, URLDb::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        UrlDao = urlDb.URLDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        urlDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInert_insertURLintoDB() = runBlocking {
        addOneURLToDB()
        val allItems = UrlDao.getAllItems().first()
        assertEquals(allItems[0].asDomainObject(), url1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllURLs_returnsAllURLsFromDB() = runBlocking {
        addTwoURLsToDB()
        val allItems = UrlDao.getAllItems().first().map { it.asDomainObject() }
        assert(allItems.contains(url1))
        assert(allItems.contains(url2))
    }
}
