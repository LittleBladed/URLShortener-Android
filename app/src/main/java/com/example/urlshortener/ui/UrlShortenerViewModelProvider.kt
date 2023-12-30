package com.example.urlshortener.ui

import UrlShortenerViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.urlshortener.URLApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            
            //als voorbeeld voor later met repository -> AppViewModel(svkApplication().container.naamRepo)
            //Vergeet de repository zeker niet toe te voegen in de interface in AppContainer.kt en dan in 
            //AppContainerData een implementatie voor die repository mee te geven
            val application = (this[APPLICATION_KEY] as URLApplication)
            val urlRepo = application.container.urlRepository
            UrlShortenerViewModel(urlRepo = urlRepo)
        }
    }
}

fun CreationExtras.svkApplication(): URLApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as URLApplication)