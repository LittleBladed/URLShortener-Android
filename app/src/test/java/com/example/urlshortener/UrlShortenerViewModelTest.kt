package com.example.templateapplication;

import UrlShortenerViewModel
import com.example.urlshortener.data.UrlShortenerRepository
import com.example.urlshortener.fake.FakeUrlShortenerRepository
import com.example.urlshortener.model.URLRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.kotlin.mock
import java.time.Instant
import java.util.Date


class AppViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private lateinit var viewModel: UrlShortenerViewModel
    private lateinit var repo: UrlShortenerRepository

    @Before
    fun setup() {
        repo = mock()
        viewModel = UrlShortenerViewModel(repo)
    }
    @Test
    fun set_LongURL(){
        val longURL = "https://google.com"
        viewModel.setLongURL(longURL)
        assert(viewModel.longURL.value == longURL)
    }

    @Test
    fun set_shortURL(){
        val shortURL = "hehehe"
        viewModel.setShortURL(shortURL)
        assert(viewModel.shortURL.value == shortURL)
    }

    @Test
    fun test_shortenURL(){
        val longURL = "https://google.com"
        val shortURL = "hehehe"
        viewModel.setLongURL(longURL)
        viewModel.setShortURL(shortURL)
        val testResult = URLRecord(shortURL, longURL, Date.from(Instant.now()))
        assert(testResult.longURL == longURL)
        assert(testResult.shortURL == shortURL)
    }



}

class TestDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
): TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }

}