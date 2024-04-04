package gay.extremist.mosaic.components.widgets

import androidx.compose.foundation.text.*
import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.*
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.TextInput
import io.ktor.websocket.Frame.*
import org.jetbrains.compose.web.css.*

@Composable
fun BasicUserInputField() {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextInput(
            text = text,
            onTextChanged = { text = it },
            modifier = Modifier.fillMaxWidth()
        )
        IconButton(onClick = {
            // Do something with the user input, e.g., show it in a dialog
            // For demonstration, we'll just print it to the console
            println("User input: $text")
        }) {
            Text("Submit")
        }
    }
}



