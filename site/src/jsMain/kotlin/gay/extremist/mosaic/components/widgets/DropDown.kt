package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.silk.components.overlay.*
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.cssRem

@Composable
fun MenuPulldown2() {
    val outerManualStrategy = remember { KeepPopupOpenStrategy.manual(defaultValue = false) }
    val outerHoverStrategy = remember { KeepPopupOpenStrategy.onHover() }
    val nestedKeepOpenStrategy = remember { KeepPopupOpenStrategy.onHover() }
    val outerKeepPopupOpen = remember { outerHoverStrategy + outerManualStrategy }
    LaunchedEffect(Unit) {
        nestedKeepOpenStrategy.keepOpenFlow
            .onEach { outerManualStrategy.shouldKeepOpen = it }
            .collect()
    }
    MenuItem("Team Select")
    Popover(
        ElementTarget.PreviousSibling,
        Modifier.margin(top = (-1).cssRem),
        keepOpenStrategy = outerKeepPopupOpen,
        hideDelayMs = 100,
    ) {
        Column(Modifier.alignItems(AlignItems.Stretch)) {
            MenuItem("Team 1")
            MenuItem("Team 2")
            MenuItem("Team 3")
            MenuItem("More")
            Popover(
                ElementTarget.PreviousSibling,
                Modifier.margin(left = (-1).cssRem),
                placement = PopupPlacement.RightTop,
                hideDelayMs = 100,
                keepOpenStrategy = nestedKeepOpenStrategy,
            ) {
                Column {
                    MenuItem("Team 4")
                    MenuItem("Team 5")
                    MenuItem("Team 6")
                }
            }
        }
    }
}


@Composable
fun MenuItem(text: String) {
    SpanText(text)
}