package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.silk.components.disclosure.Tabs
import com.varabyte.kobweb.silk.components.forms.FilledInputVariant
import com.varabyte.kobweb.silk.components.forms.InputGroup
import com.varabyte.kobweb.silk.components.forms.InputSize
import com.varabyte.kobweb.silk.components.forms.TextInput
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun AccountInfo(onAction: (String, String, String) -> Unit) {
    Column(Modifier.gap(0.5.cssRem), verticalArrangement = Arrangement.Center, ) {

        SpanText("Current Email")
        SpanText("Current Username")
        SpanText("Current Password")



        var email by remember { mutableStateOf("") }
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        InputGroup( size = InputSize.LG) {
            TextInput(
                email,
                placeholder = "email",
                variant = FilledInputVariant,
                onTextChanged = { email = it })
        }


        InputGroup(size = InputSize.LG) {
            TextInput(
                username,
                placeholder = "username",
                variant = FilledInputVariant,
                onTextChanged = { username = it })
        }


        InputGroup(size = InputSize.LG) {
            TextInput(
                password,
                placeholder = "username",
                variant = FilledInputVariant,
                onTextChanged = { password = it })
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { onAction(email, username, password) }
            ) {
                Text("Submit")
            }
        }
        Tabs(Modifier.fontSize(1.3.cssRem)) {
            TabPanel {
                Tab { Text("Tags") }; Panel { Text("Tag List") }
            }
            TabPanel {
                Tab { Text("Creators") }; Panel { Text("Creator List") }
            }
        }

    }
}
