import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.urlshortener.navigation.NavigationEnums

/**
 * Composable function displaying a loading screen.
 * This screen is shown while the application is performing a URL shortening operation.
 * For Android versions below S, it displays a message indicating the need for an update.
 *
 * @param navController The navigation controller used for navigation between composables.
 * @param viewModel The ViewModel associated with the URL shortener.
 */
@Composable
fun LoadingScreen(navController: NavHostController, viewModel: UrlShortenerViewModel) {
    val context = LocalContext.current

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        print("Loading screen")

        LaunchedEffect(Unit) {
            viewModel.shortenUrl(navController)
        }

        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            Text(
                "Loading...",
                color = Color.Gray,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    } else {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                "Update Android version to use this feature",
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}