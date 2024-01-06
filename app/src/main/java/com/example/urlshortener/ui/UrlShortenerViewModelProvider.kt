package com.example.urlshortener.ui

import UrlShortenerViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.urlshortener.URLApplication

/**
 * Provides a factory for creating ViewModel instances.
 * This factory ensures that the ViewModel instances are provided with necessary dependencies.
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val application = (this[APPLICATION_KEY] as URLApplication)
            val urlRepo = application.container.urlRepository
            UrlShortenerViewModel(urlRepo = urlRepo)
        }
    }
}

/**
 * Extension function to retrieve the URLApplication instance from CreationExtras.
 *
 * @return The URLApplication instance.
 */
fun CreationExtras.svkApplication(): URLApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as URLApplication)
