import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.urlshortener.navigation.NavigationEnums

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: UrlShortenerViewModel) {
    var longText by remember { mutableStateOf("") }
    var shortText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = longText,
            onValueChange = { longText = it },
            label = { Text("Enter URL") }
        )
        OutlinedTextField(
            value = shortText,
            onValueChange = { shortText = it },
            label = { Text("Enter short URL") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.setLongURL(longText)
            viewModel.setShortURL(shortText)
            print("SET LONG URL")
            navController.navigate(NavigationEnums.LOADING.name)
        }) {
            Text("Shorten URL")
        }
    }
}