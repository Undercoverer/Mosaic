package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.*
import com.varabyte.kobweb.silk.components.icons.ChevronRightIcon
import com.varabyte.kobweb.silk.components.icons.PlusIcon
import com.varabyte.kobweb.silk.components.overlay.*
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.cssRem


@Composable
fun RatingFunc(onAction: (Int) -> Unit){

    var rating by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var isPopoverVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.borderRadius(1.cssRem),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = { isPopoverVisible = !isPopoverVisible  }
        ) {
            PlusIcon()
        }
    }
    if (isPopoverVisible) {
        Popover(
            target = ElementTarget.PreviousSibling,
            modifier = Modifier.padding(1.cssRem).background(when (ColorMode.current) {
                ColorMode.LIGHT -> Colors.LightGray
                ColorMode.DARK -> Color.rgb(0x2B2B2B)
            }),
            placement = PopupPlacement.TopRight,
            keepOpenStrategy = KeepPopupOpenStrategy.manual(true),
            content = {
                Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) { // Ensure the row lays out items vertically aligned
                    InputGroup(Modifier.width(7.cssRem), size = InputSize.LG) {
                        TextInput(
                            rating,
                            placeholder = "Rate",
                            variant = FilledInputVariant,
                            onTextChanged = { newText ->
                                // Reset the error flag when the user starts typing
                                isError = false
                                // Only update the rating if the new text is empty or a valid number between 1 and 5
                                if (newText.isEmpty() || newText.toIntOrNull() in 1..5) {
                                    rating = newText
                                } else {
                                    // If the input is invalid, set the error flag to true
                                    isError = true
                                }},
                        )
                        RightInset(Modifier.width(3.cssRem)) {
                            Button(
                                onClick = {
                                    if (rating.toIntOrNull() in 1..5) {
                                        onAction(rating.toInt())
                                    } else {
                                        // Show error message as a tooltip if rating is not within range
                                        isError = true
                                    }},
                                size = ButtonSize.SM,
                            ) {
                                ChevronRightIcon()
                            }
                        }



                    }
                    if (isError) {
                        Tooltip(ElementTarget.PreviousSibling, "Enter a value between 1 and 5", placement = PopupPlacement.BottomRight)

                    }

                }

            }
        )
    }

    // Add an invisible target element for the tooltip
    Box(modifier = Modifier.id("rating-tooltip"))
}
