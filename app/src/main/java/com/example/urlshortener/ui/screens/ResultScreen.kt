import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.urlshortener.navigation.NavigationEnums
import com.example.urlshortener.ui.AppViewModelProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import android.widget.Toast

import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.res.stringResource
import com.example.urlshortener.R

/**
 * Composable function that displays the results screen after a URL has been shortened.
 * It provides options to copy the shortened URL to the clipboard and share it.
 *
 * @param navController The navigation controller used for navigating between screens.
 * @param viewModel The ViewModel associated with the URL shortener, providing the shortened URL.
 */
@Composable
fun ResultsScreen(navController: NavHostController, viewModel: UrlShortenerViewModel) {
    val shortURL by viewModel.shortURL.collectAsState()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Your shortened URL",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = AnnotatedString(shortURL),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        textDecoration = TextDecoration.Underline
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    // Copy to clipboard
                    IconButton(onClick = {
                        clipboardManager.setText(AnnotatedString(shortURL))
                        // Provide feedback to the user (e.g., a Toast or SnackBar)
                        Toast.makeText(context, "URL copied to clipboard", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Filled.ContentCopy, contentDescription = "Copy")
                    }
                    // Share
                    IconButton(onClick = {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, shortURL)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    }) {
                        Icon(Icons.Filled.Share, contentDescription = "Share")
                    }
                }
                Button(
                    onClick = {
                        // Navigate back to the home screen
                        navController.navigate(NavigationEnums.HOME.name) {
                            // Clear back stack to prevent going back to the results screen
                            popUpTo(NavigationEnums.HOME.name) {
                                inclusive = true
                            }
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Icon(Icons.Filled.Home, contentDescription = "Go Back Button")
                    Text(text = "Go Back")
                }
            }
        }
    }
}