package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.disclosure.Tabs
import com.varabyte.kobweb.silk.components.forms.*
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun AccountInfo(onAction: (String, String, String) -> Unit) {
    Column(Modifier.gap(0.5.cssRem), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        val ctx = rememberPageContext()
        Button(
            onClick = {
                ctx.router.tryRoutingTo("/creator")
            },
            Modifier.background(Color.rgb(0x2EB4A9))
        ) {
            Text("My Creator Page")
        }

        SpanText("Current Email")
        SpanText("Current Username")
        SpanText("Current Password")

        Row(Modifier.height(1.cssRem)){}

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
            Button(
                onClick = {
                    onAction(email, username, password)
                },
                Modifier.background(Color.rgb(0x2EB4A9))
            ) {
                Text("Submit")
            }
        }
        Row(Modifier.height(1.cssRem)){}
        Tabs(Modifier.fontSize(1.3.cssRem)) {
            TabPanel {
                Tab { Text("Creators") }; Panel {

                    Box(Modifier.fillMaxSize().height(10.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                        Column(Modifier.gap(0.2.cssRem).fillMaxSize()){
                            val ctx = rememberPageContext()
                            for (index in 1..25) {
                                Link("/creator", "Creator " + index, Modifier.color(Colors.DarkBlue))
                            }

                        }

                    }

                }
            }
            TabPanel {
                Tab { Text("Tags") }; Panel {
                    Box(Modifier.fillMaxSize().height(10.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                        Column(Modifier.gap(0.2.cssRem).fillMaxSize()){
                            val ctx = rememberPageContext()
                            for (index in 1..25) {
                                Link("/tag", "Tag " + index, Modifier.color(Colors.DarkBlue))
                            }

                        }

                    }

                }
            }
        }

    }
}
