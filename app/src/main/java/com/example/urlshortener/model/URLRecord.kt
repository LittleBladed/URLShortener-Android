package com.example.urlshortener.model

import java.util.Date

data class URLRecord (
    var shortURL: String,
    var longURL: String,
    var date: Date
)