import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.urlshortener.navigation.NavigationEnums


/**
 * Checks if the network is available.
 *
 * @param context The context used to access the connectivity service.
 * @return Boolean indicating whether network is available.
 */
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
}

/**
 * Composable function that displays the home screen for the URL shortener application.
 *
 * @param navController The navigation controller used for navigation between composables.
 * @param viewModel The ViewModel associated with the URL shortener.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: UrlShortenerViewModel) {
    var longText by remember { mutableStateOf("") } // State for storing the long URL input
    var shortText by remember { mutableStateOf("") } // State for storing the short URL input
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current // Local context for the toast message

    val focusRequester = remember { FocusRequester() } // Focus management for text fields

    // Layout for the home screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Text field for entering the long URL
        OutlinedTextField(
            value = longText,
            onValueChange = { longText = it },
            label = { Text("Enter URL") },
            leadingIcon = { Icon(Icons.Filled.Link, contentDescription = "URL icon") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Text field for entering the short URL (optional)
        OutlinedTextField(
            value = shortText,
            onValueChange = { shortText = it },
            label = { Text("Preferred short (optional)") },
            leadingIcon = { Icon(Icons.Filled.Link, contentDescription = "Short URL icon") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                viewModel.setLongURL(longText)
                viewModel.setShortURL(shortText)
                navController.navigate(NavigationEnums.LOADING.name)
                keyboardController?.hide()
            }),
            modifier = Modifier.focusRequester(focusRequester),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Button to shorten URL
        Button(
            onClick = {
                if (isNetworkAvailable(context)) { // Check for network availability
                    viewModel.setLongURL(longText)
                    viewModel.setShortURL(shortText)
                    navController.navigate(NavigationEnums.LOADING.name)
                } else {
                    Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text("Shorten URL", color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Button to navigate to history screen
        Button(
            onClick = { navController.navigate(NavigationEnums.HISTORY.name) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Icon(Icons.Filled.History, contentDescription = "Check History")
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Check History", color = Color.White)
        }
    }
}