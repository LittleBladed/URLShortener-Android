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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.urlshortener.navigation.NavigationEnums
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: UrlShortenerViewModel) {
    var longText by remember { mutableStateOf("") }
    var shortText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
        Button(
            onClick = {
                viewModel.setLongURL(longText)
                viewModel.setShortURL(shortText)
                navController.navigate(NavigationEnums.LOADING.name)
            },

            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text("Shorten URL", color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
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
