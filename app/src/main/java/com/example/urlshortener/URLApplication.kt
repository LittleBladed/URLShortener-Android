package com.example.urlshortener

import android.app.Application
import com.example.urlshortener.data.AppContainer
import com.example.urlshortener.data.DefaultAppContainer

class URLApplication : Application(){
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context=applicationContext)
    }
}