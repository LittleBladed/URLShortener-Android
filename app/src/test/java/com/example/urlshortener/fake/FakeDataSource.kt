package com.example.urlshortener.fake

import com.example.urlshortener.data.database.dbURLRecord
import com.example.urlshortener.model.URLRecord
import java.time.Instant
import java.util.Date

object FakeDataSource {
    const val shortURL1 = "hehehe"
    const val shortURL2 = "hahaha"
    const val longURL1 = "https://youtube.com"
    const val longURL2 = "https://google.com"

    val records = listOf(
        dbURLRecord(shortURL1, longURL1, Date.from(Instant.now())),
        dbURLRecord(shortURL2, longURL2, Date.from(Instant.now()))
    )

}