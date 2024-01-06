import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.urlshortener.navigation.NavigationEnums
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.urlshortener.data.UrlShortenerRepository
import com.example.urlshortener.model.URLRecord
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel for the URL shortener application.
 * Manages the UI-related data and handles the communication between the UI and the repository.
 *
 * @property urlRepo The repository for URL shortening operations.
 */
class UrlShortenerViewModel(private val urlRepo: UrlShortenerRepository) : ViewModel() {
    private var _longURL = MutableStateFlow("")
    val longURL: StateFlow<String> = _longURL

    private var _shortURL = MutableStateFlow("")
    val shortURL: StateFlow<String> = _shortURL

    init {
        println("vm inspection UrlShortenerViewModel init")
    }

    /**
     * Sets the long URL to be shortened.
     *
     * @param newUrl The new long URL to be set.
     */
    fun setLongURL(newUrl: String) {
        _longURL.value = newUrl
    }

    /**
     * Retrieves the history of shortened URLs as a flow.
     *
     * @return A Flow emitting a list of URLRecords.
     */
    fun getURLHistory(): Flow<List<URLRecord>> {
        return urlRepo.getURLHistory()
    }

    /**
     * Sets the desired short URL.
     *
     * @param newUrl The new short URL to be set.
     */
    fun setShortURL(newUrl: String) {
        _shortURL.value = newUrl
    }

    /**
     * Initiates the URL shortening process and navigates to the result screen upon success.
     *
     * @param navCont The navigation controller used for navigating to the results screen.
     */
    fun shortenUrl(navCont: NavHostController) {
        viewModelScope.launch {
            try {
                val response = urlRepo.shortenURL(_longURL.value, _shortURL.value)
                _shortURL.value = "https://1pt.co/" + response.shortURL
                navCont.navigate(NavigationEnums.RESULTS.name)
            } catch (e: Exception) {
                // Handle exception
                print("---------------- EXCEPTION")
            }
        }
    }
}