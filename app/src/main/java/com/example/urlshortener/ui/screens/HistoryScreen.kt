import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.urlshortener.navigation.NavigationEnums
import com.example.urlshortener.ui.AppViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign

/**
 * Composable function that displays the history screen for the URL shortener application.
 *
 * @param navController The navigation controller for navigation events.
 * @param viewModel The view model associated with the URL shortener.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavHostController, viewModel: UrlShortenerViewModel) {
    // Collecting the URL history state from the ViewModel
    val urlHistory = viewModel.getURLHistory().collectAsState(initial = listOf()).value

    // Sorting the history in descending order based on date
    val sortedHistory = urlHistory.sortedByDescending { it.date.time }

    // TopAppBar showing the title 'History'
    TopAppBar(
        title = { Text("History", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) }
    )

    // LazyColumn to list each URL record
    LazyColumn(modifier = Modifier.padding(16.dp)) {

        // Looping through each URL record in the sorted history
        items(sortedHistory) { urlRecord ->
            // Constructing the actual short URL
            var actualShort = "https://1pt.co/" + urlRecord.shortURL

            // Card UI for each URL record
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    // Displaying the short URL
                    Text(
                        text = "Short URL: $actualShort",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    // Displaying the long URL
                    Text(
                        text = "Long URL: ${urlRecord.longURL}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Displaying the timestamp
                    Text(
                        text = "Timestamp: ${formatTimestamp(urlRecord.date.time)}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    // Row for action buttons (copy and share)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        // Clipboard manager to copy text
                        val clipboardManager = LocalClipboardManager.current
                        // Context for showing the toast
                        val context = LocalContext.current

                        // Button to copy the URL to clipboard
                        IconButton(onClick = {
                            clipboardManager.setText(AnnotatedString(actualShort))
                            Toast.makeText(context, "Short URL copied to clipboard", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(Icons.Filled.ContentCopy, contentDescription = "Copy")
                        }

                        // Button to share the URL
                        IconButton(onClick = {
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, actualShort)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            context.startActivity(shareIntent)
                        }) {
                            Icon(Icons.Filled.Share, contentDescription = "Share")
                        }
                    }
                }
            }
        }
    }
}

/**
 * Formats a given timestamp into a human-readable date and time string.
 *
 * @param timestamp The timestamp to format.
 * @return A string representing the formatted date and time.
 */
fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
