package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.forms.*
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text

@Composable
fun RegisterFunc(onAction: (String, String, String) -> Unit) {
    Column(Modifier.gap(0.5.cssRem), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        var email by remember { mutableStateOf("") }
        InputGroup( size = InputSize.LG) {
            TextInput(
                email,
                placeholder = "Email",
                variant = FilledInputVariant,
                onTextChanged = { email = it })
        }

        var username by remember { mutableStateOf("") }
        InputGroup(size = InputSize.LG) {
            TextInput(
                username,
                placeholder = "Username",
                variant = FilledInputVariant,
                onTextChanged = { username = it })
        }

        var showPassword by remember { mutableStateOf(false) }
        var password by remember { mutableStateOf("") }
        InputGroup(Modifier.width(247.px), size = InputSize.LG) {
            TextInput(
                password,
                placeholder = "Password",
                variant = FilledInputVariant,
                password = !showPassword,
                onTextChanged = { password = it },

                )
            RightInset(width = 4.5.cssRem) {
                Button(
                    onClick = { showPassword = !showPassword },
                    Modifier.width(3.5.cssRem).height(1.75.cssRem).background(Color.rgb(0x2A9F96)),
                    size = ButtonSize.SM,
                ) {
                    Text(if (showPassword) "Hide" else "Show")
                }
            }
        }

        Button(
            onClick = {
                if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    // Additionally, perform the action associated with onAction
                    onAction(email, username, password)
                } else {
                    println("Cannot navigate to /home. All fields must be filled.")
                }
            },
            Modifier.background(Color.rgb(0x2A9F96))
        ) {
            Text("Register")
        }
    }
}
