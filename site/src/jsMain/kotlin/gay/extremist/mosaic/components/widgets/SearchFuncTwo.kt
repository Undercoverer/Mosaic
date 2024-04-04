package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.*
import com.varabyte.kobweb.silk.components.icons.fa.FaMagnifyingGlass
import com.varabyte.kobweb.silk.components.style.addVariantBase
import com.varabyte.kobweb.silk.components.style.vars.color.ColorVar
import com.varabyte.kobweb.silk.theme.colors.palette.background
import com.varabyte.kobweb.silk.theme.colors.palette.toPalette
import io.ktor.client.*
import org.jetbrains.compose.web.attributes.ButtonType
import org.jetbrains.compose.web.attributes.onSubmit
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Form
import org.jetbrains.compose.web.dom.Text


val SearchBarInput by InputGroupStyle.addVariantBase {
    Modifier
        .setVariable(InputVars.BorderColor, Colors.Transparent)
        .setVariable(ColorVar, colorMode.toPalette().background)
        .backgroundColor(Color.rgba(220, 233, 250, 0.2f).darkened(0.1f))
}

@Composable
fun SearchForm(httpClient: HttpClient) {
    Form(
        attrs = Modifier
            .fillMaxWidth()
            .margin(topBottom = 0.5.cssRem)
            .textAlign(TextAlign.Center)
            .toAttrs {
                onSubmit {
                    it.preventDefault() // This stops the form from "submitting"
                    // TODO Perform search
                }
            }
    ) {
        val dataListId = "search-list"
//        key(/**TODO Create suggestions list**/) {
//            Datalist(Modifier.id(dataListId).toAttrs()) {
//                viewModel.suggestions.forEach {
//                    Option(it) // tried using key() but it didn't seem to help
//                }
//            }
//        }
        SearchBar(dataListId, httpClient)
        Text("Search and select an instructor or subject, or enter a course code")
    }
}

@Composable
private fun SearchBar(dataListId: String, httpClient: HttpClient) {
    var textInput by remember { mutableStateOf("") }
    InputGroup(Modifier.margin(bottom = 0.25.cssRem), variant = SearchBarInput) {
        TextInput(
            textInput,
            { textInput = it },
            Modifier.onClick {  }.attrsModifier { attr("list", dataListId) },
            placeholder = "",
            focusBorderColor = Colors.Transparent,
        )
        RightInset {
            Button(
                onClick = {},
                modifier = Modifier.ariaLabel("Search"),
                variant = SearchBarInput,
                type = ButtonType.Submit,
            ) {
                FaMagnifyingGlass()
            }
        }
    }
}
