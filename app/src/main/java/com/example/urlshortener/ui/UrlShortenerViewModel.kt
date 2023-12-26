import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.urlshortener.data.UrlShortenerService
import com.example.urlshortener.navigation.NavigationEnums
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Log


class UrlShortenerViewModel : ViewModel() {
    private var _longURL = MutableStateFlow("")
    val longURl: StateFlow<String> = _longURL

    private var _shortURL = MutableStateFlow("")
    val shortURL: StateFlow<String> = _shortURL

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://csclub.uwaterloo.ca/~phthakka/1pt-express/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(UrlShortenerService::class.java)

    fun setLongURL(newUrl: String) {
        _longURL.value = newUrl
    }



    fun setShortURL(newUrl: String) {
        _shortURL.value = newUrl
    }

    fun shortenUrl(navCont: NavHostController) {
        Log.d("UrlShortenerViewModel", "WENOBEDOING")
        viewModelScope.launch {
            Log.d("UrlShortenerViewModel", "WEBEDOING")
            try {
                val response = apiService.addURL(_longURL.value, _shortURL.value)
                if (response.isSuccessful && response.body() != null) {
                    Log.d("UrlShortenerViewModel", "ABABABABABAB" + response.body())
                    _shortURL.value = "https://1pt.co/" + response.body()!!.short
                    Log.d("UrlShortenerViewModel", "ABABABABABAB"+ shortURL.value)
                    navCont.navigate(NavigationEnums.RESULTS.name)
                } else {
                    Log.d("UrlShortenerViewModel", "Error Response: " + response.errorBody()?.string())
                    Log.d("UrlShortenerViewModel", "HTTP Status Code: " + response.code())
                }
            } catch (e: Exception) {
                // Handle exception
                print("---------------- EXCEPTION")
            }
        }
    }
}