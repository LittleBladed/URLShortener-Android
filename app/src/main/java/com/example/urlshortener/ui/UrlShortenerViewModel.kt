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


class UrlShortenerViewModel(private val urlRepo: UrlShortenerRepository) : ViewModel() {
    private var _longURL = MutableStateFlow("")
    val longURL: StateFlow<String> = _longURL

    private var _shortURL = MutableStateFlow("")
    val shortURL: StateFlow<String> = _shortURL

    init{
        println("vm inspection UrlShortenerViewModel init")
    }

    fun setLongURL(newUrl: String) {
        _longURL.value = newUrl
    }

    fun getURLHistory(): Flow<List<URLRecord>> {
        return urlRepo.getURLHistory()
    }



    fun setShortURL(newUrl: String) {
        _shortURL.value = newUrl
    }

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