package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.*
import com.varabyte.kobweb.silk.components.icons.fa.FaCheck
import com.varabyte.kobweb.silk.components.icons.fa.FaDollarSign
import com.varabyte.kobweb.silk.theme.colors.ColorSchemes
import org.jetbrains.compose.web.attributes.AutoComplete
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Hr
import org.jetbrains.compose.web.dom.Text

@Composable
fun UploadData(){
    var text by remember { mutableStateOf("") }
    Column(Modifier.gap(0.5.cssRem).fillMaxWidth()) {
        Row(Modifier.fillMaxWidth()) {
            Column(Modifier.gap(0.5.cssRem)) {
                TextInput(
                    text,
                    placeholder = "extra small size",
                    size = InputSize.XS,
                    onTextChanged = { text = it })
                TextInput(
                    text,
                    placeholder = "small size",
                    size = InputSize.SM,
                    onTextChanged = { text = it })
                TextInput(
                    text,
                    placeholder = "medium size",
                    size = InputSize.MD,
                    onTextChanged = { text = it })
                TextInput(
                    text,
                    placeholder = "large size",
                    size = InputSize.LG,
                    onTextChanged = { text = it })
            }

            Spacer()

            Column(Modifier.gap(0.5.cssRem)) {
                TextInput(
                    text,
                    placeholder = "outlined",
                    variant = OutlinedInputVariant,
                    onTextChanged = { text = it })
                TextInput(
                    text,
                    placeholder = "filled",
                    variant = FilledInputVariant,
                    onTextChanged = { text = it })
                TextInput(
                    text,
                    placeholder = "flushed",
                    variant = FlushedInputVariant,
                    onTextChanged = { text = it })
                TextInput(
                    text,
                    placeholder = "unstyled",
                    variant = UnstyledInputVariant,
                    onTextChanged = { text = it })
            }
        }

        Hr(Modifier.fillMaxWidth().toAttrs())

        Row(Modifier.gap(0.5.cssRem).fillMaxWidth().flexWrap(FlexWrap.Wrap)) {
            Column(Modifier.gap(0.5.cssRem)) {
                var telNum by remember { mutableStateOf("") }
                InputGroup {
                    LeftAddon { Text("+1") }
                    Input(
                        InputType.Tel,
                        telNum,
                        placeholder = "phone number",
                        autoComplete = AutoComplete.telNational,
                        onValueChanged = { telNum = it })
                }

                var url by remember { mutableStateOf("") }
                InputGroup(size = InputSize.SM) {
                    LeftAddon { Text("https://") }
                    TextInput(url, placeholder = "url", onTextChanged = { url = it })
                    RightAddon { Text(".com") }
                }

                var dateTime by remember { mutableStateOf("") }
                Input(InputType.DateTimeLocal, dateTime, onValueChanged = { dateTime = it })
            }
        }
        val dollarRegex = Regex("""^(\d{1,3}(,\d{3})*|(\d+))(\.\d{2})?$""")
        var amount by remember { mutableStateOf("") }
        InputGroup(size = InputSize.SM) {
            LeftInset { FaDollarSign() }
            TextInput(
                amount,
                placeholder = "amount",
                onTextChanged = { amount = it })
            RightInset {
                if (dollarRegex.matches(amount)) {
                    FaCheck(Modifier.color(ColorSchemes.Green._500))
                }
            }
        }
    }
}
