package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.foundation.text.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.width
import io.ktor.websocket.Frame.*
import org.jetbrains.compose.web.css.cssRem
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.*
import com.varabyte.kobweb.core.rememberPageContext
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import androidx.compose.foundation.text.BasicTextField
import com.varabyte.kobweb.compose.css.Appearance.Companion.TextField
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun BasicUserInputField() {
    var text by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth()
        )
        IconButton(
            onClick = {
                // Do something with the user input, e.g., show it in a dialog
                // For demonstration, we'll just print it to the console
                println("User input: ${text.text}")
            }
        ) {
            Text("Submit")
        }
    }
}



