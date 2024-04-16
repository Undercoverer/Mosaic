package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontSize
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
import gay.extremist.mosaic.Util.getRequest
import gay.extremist.mosaic.Util.headerToken
import gay.extremist.mosaic.data_models.AccountDisplayResponse
import gay.extremist.mosaic.data_models.AccountResponse
import gay.extremist.mosaic.data_models.TagDisplayResponse
import gay.extremist.mosaic.data_models.VideoResponse
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.get

@Composable
fun AccountInfo(onAction: (String, String, String, String) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val loadingVal = "Loading..."

    var account by remember {
        mutableStateOf(
            AccountResponse(
                accountID = -1,
                username = loadingVal,
                email = loadingVal,
                token = loadingVal,
                password = loadingVal
            )
        )
    }

    var followedCreators by remember {
        mutableStateOf(listOf<AccountDisplayResponse>())
    }

    var followedTags by remember {
        mutableStateOf(listOf<TagDisplayResponse>())
    }

    coroutineScope.launch {
        account = getRequest<AccountResponse>(
            urlString = "accounts/${window.localStorage["id"]}",
            setHeaders = {
                append(headerToken, window.localStorage["token"] ?: "")
            },
            onError = {
                println(it.message)
            }
        ) ?: account

        followedCreators = getRequest<List<AccountDisplayResponse>>(
            urlString = "accounts/${window.localStorage["id"]}/following",
            setHeaders = {
                append(headerToken, window.localStorage["token"] ?: "")
            },
            onError = {
                println(it.message)
            }
        ) ?: followedCreators

        followedTags = getRequest<List<TagDisplayResponse>>(
            urlString = "accounts/${window.localStorage["id"]}/tags",
            setHeaders = {
                append(headerToken, window.localStorage["token"] ?: "")
            },
            onError = {
                println(it.message)
            }
        ) ?:followedTags
    }

    Column(Modifier.gap(0.5.cssRem), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        val ctx = rememberPageContext()
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    ctx.router.tryRoutingTo("/creator/${window.localStorage["id"]}")
                },
                Modifier.background(Color.rgb(0x2EB4A9))
            ) {
                Text("My Creator Page")
            }
        }

        SpanText("Email: ${account.email}")
        SpanText("Username: ${account.username}")

        Row(Modifier.height(1.cssRem)){}

        var email by remember { mutableStateOf("") }
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var showPassword by remember { mutableStateOf(false) }
        var confirmPassword by remember { mutableStateOf("")}
        var showConfirmPassword by remember { mutableStateOf(false) }

        val inputWidth = Modifier.width(15.cssRem)



        InputGroup( size = InputSize.LG, modifier = inputWidth) {
            TextInput(
                email,
                placeholder = "Email",
                variant = FilledInputVariant,
                onTextChanged = { email = it })
        }


        InputGroup(size = InputSize.LG, modifier = inputWidth) {
            TextInput(
                username,
                placeholder = "Username",
                variant = FilledInputVariant,
                onTextChanged = { username = it })
        }


        InputGroup(size = InputSize.LG, modifier = inputWidth) {
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
                    Modifier.width(3.5.cssRem).height(1.75.cssRem).background(Color.rgb(0x2EB4A9)),
                    size = ButtonSize.SM,
                ) {
                    Text(if (showPassword) "Hide" else "Show")
                }
            }
        }

        InputGroup(size = InputSize.LG, modifier = inputWidth) {
            TextInput(
                confirmPassword,
                placeholder = "Confirm Password",
                variant = FilledInputVariant,
                password = !showConfirmPassword,
                onTextChanged = { confirmPassword = it },
            )
            RightInset(width = 4.5.cssRem) {
                Button(
                    onClick = { showConfirmPassword = !showConfirmPassword },
                    Modifier.width(3.5.cssRem).height(1.75.cssRem).background(Color.rgb(0x2EB4A9)),
                    size = ButtonSize.SM,
                ) {
                    Text(if (showConfirmPassword) "Hide" else "Show")
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    onAction(email, username, password, confirmPassword)
                },
                Modifier.background(Color.rgb(0x2EB4A9))
            ) {
                Text("Submit")
            }
        }
        Row(Modifier.height(1.cssRem)){}
        Tabs(Modifier.fillMaxSize().fontSize(FontSize.Smaller)) {
            TabPanel {
                Tab { Text("Followed Creators") }; Panel {

                    Box(Modifier.fillMaxSize().height(10.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                        Column(Modifier.gap(0.2.cssRem).fillMaxSize()){
                            for (creator in followedCreators) {
                                Link("/creator/${creator.id}", creator.username, Modifier.color(Colors.DarkBlue))
                            }

                        }

                    }

                }
            }
            TabPanel {
                Tab { Text("Followed Tags") }; Panel {
                    Box(Modifier.fillMaxSize().height(10.cssRem).position(Position.Relative).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                        Column(Modifier.gap(0.2.cssRem).fillMaxSize()){
                            for (tag in followedTags) {
                                Link("/tags/${tag.id}", tag.tag, Modifier.color(Colors.DarkBlue))
                            }

                        }

                    }

                }
            }
        }

    }
}
