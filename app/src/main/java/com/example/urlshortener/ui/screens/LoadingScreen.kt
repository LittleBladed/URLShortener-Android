import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.urlshortener.navigation.NavigationEnums

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun LoadingScreen(navController: NavHostController, viewModel: UrlShortenerViewModel) {
    print("Loading screenQSDQSD")
    LaunchedEffect(Unit) {
        viewModel.shortenUrl(navController)
//        navController.navigate(NavigationEnums.RESULTS.name)
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text("Loading...")
    }
}
