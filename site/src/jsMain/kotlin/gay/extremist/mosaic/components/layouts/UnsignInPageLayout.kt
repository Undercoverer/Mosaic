package gay.extremist.mosaic.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.dom.svg.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.components.sections.Footer
import gay.extremist.mosaic.components.sections.UnsignInNavHeader
import gay.extremist.mosaic.toSitePalette
import kotlinx.browser.document
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.percent

val UnsignInPageContentStyle by ComponentStyle {
    base { Modifier.fillMaxSize().padding(leftRight = 2.cssRem, top = 0.20.cssRem) }
    Breakpoint.MD { Modifier.maxWidth(100.cssRem) }
}

// NOTE: This is a fun little graphic that showcases what you can do with SVG. However, this probably does not make
// sense in your own site, so feel free to delete it.
@Composable
private fun SvgMosaic(modifier: Modifier) {
    val color = ColorMode.current.toSitePalette().cobweb

    // On mobile, the SVG would cause scrolling, so clamp its max width
    Svg(attrs = modifier.maxWidth(100.percent).toAttrs {
        width(55.cssRem)
        height(40.cssRem)
    }) {
        val mosaicFadeOutId = SvgId("mosaic-fade-out")
        Defs {
            // Fade out the bottom right of the cobweb with a circular shape
            RadialGradient(mosaicFadeOutId, attrs = {
                cx(0)
                cy(0)
                r(120.percent)
            }) {
                Stop(10.percent, color)
                Stop(90.percent, color, stopOpacity = 0f)
            }
        }

        Path {
            // d { ... } is useful for type-safe, readable SVG path construction, but I got a complex path from a
            // designer, so using d(...) directly in this case

            d("m 539.1067,2.1141438 h 56.55335 L 578.7469,11.627792 Z m 0.83934,41.3816282 37.66093,-10.608725 -24.4,15.913088 z M 508.13128,30.232966 532.53135,6.8937702 593.53151,24.928603 529.87916,39.780818 Z m -64.179,-28.6454578 80.62631,1.0608725 -26.52183,25.4609363 -22.80873,-7.426108 z m 33.41368,28.1093208 2.65219,28.113116 66.83497,-3.71305 z m -76.90754,35.014483 38.19142,-60.4697223 29.17397,21.2174463 3.71307,33.417487 z m -11.66958,10.06689 122.00031,-9.547852 -96.53938,30.765301 z m -130.49109,36.592508 50.92183,11.13916 -6.89565,16.97397 -23.33917,-5.30439 z m -2.64268,-7.94323 122.53072,-25.991397 28.11313,20.687011 -13.79136,33.417456 z M 113.50004,256.72165 94.934779,314.53917 151.69147,293.85215 Z m 30.77289,-75.31626 71.07846,-21.74787 -50.39144,54.10448 z m -8.48694,2.12171 -18.03483,66.83497 58.34794,54.10447 -17.50438,-84.86973 z m 21.22507,-63.65046 83.27846,-13.79129 28.64354,25.99135 -127.3047,41.37399 z M 1.5856079,103.43128 v 51.98269 L 74.785803,84.335553 66.829256,71.07465 Z M 2.1160442,1.5875082 1.5856079,93.352973 66.829256,62.057231 65.768385,1.5875082 Z m 0,340.0096118 v 64.71321 L 74.785803,327.80579 Z m 0,-100.78293 v 91.76549 L 78.498854,317.19706 Z M 116.69031,161.77922 49.855303,276.3535 86.455401,312.95357 128.35988,181.4054 Z M 43.490067,126.77049 79.559727,92.822534 110.32499,149.0488 Z M 75.316236,66.300721 137.37727,36.065865 147.98599,119.34433 132.60336,171.3271 Z M 152.75989,80.622502 156.473,110.85737 241.87323,94.944278 182.46436,35.535425 Z M 308.70821,7.4223063 301.81246,83.274681 379.25616,68.952905 Z m 6.8956,-5.8347981 110.86123,0.5304362 -36.06973,63.1219046 z M 271.04721,1.057072 h 29.7044 l -8.48695,84.869793 -38.19142,7.95654 -41.37398,-42.965326 z m -63.65237,0.5304363 h 50.39144 l -50.39144,44.0262087 -18.0348,-16.97396 z m -64.71325,-1e-7 54.10454,0.5304362 -45.08709,65.2436486 z m -68.956659,0 1.060872,55.1653658 63.121947,-29.17399 -4.77397,-25.9913758 z M 1.585608,167.08362 2.6464804,229.14462 44.55094,269.45776 108.73371,159.12709 36.063961,134.19652 Z")
            transform { scale(1.5) }
            fill(mosaicFadeOutId)
        }

    }
}

@Composable
fun UnsignInPageLayout(title: String, content: @Composable ColumnScope.() -> Unit) {
    LaunchedEffect(title) {
        document.title = "Mosaic - $title"
    }

    Box(
        Modifier
            .fillMaxWidth()
            .minHeight(100.percent)
            // Create a box with two rows: the main content (fills as much space as it can) and the footer (which reserves
            // space at the bottom). "min-content" means the use the height of the row, which we use for the footer.
            // Since this box is set to *at least* 100%, the footer will always appear at least on the bottom but can be
            // pushed further down if the first row grows beyond the page.
            // Grids are powerful but have a bit of a learning curve. For more info, see:
            // https://css-tricks.com/snippets/css/complete-guide-grid/
            .gridTemplateRows { size(1.fr); size(minContent) },
        contentAlignment = Alignment.Center
    ) {
        SvgMosaic(Modifier.gridRow(1).align(Alignment.TopStart))
        Column(
            // Isolate the content, because otherwise the absolute-positioned SVG above will render on top of it.
            // This is confusing but how browsers work. Read up on stacking contexts for more info.
            // https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_positioned_layout/Understanding_z-index/Stacking_context
            // Some people might have used z-index instead, but best practice is to avoid that if possible, because
            // as a site gets complex, Z-fighting can be a huge pain to track down.
            Modifier.fillMaxSize().gridRow(1),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            UnsignInNavHeader()
            Column(
                UnsignInPageContentStyle.toModifier(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
                //VideoPlayer("player","https://dash.akamaized.net/envivio/EnvivioDash3/manifest.mpd", height = 500, width = 500)
            }
        }


        // Associate the footer with the row that will get pushed off the bottom of the page if it can't fit.
        Footer(Modifier.fillMaxWidth().gridRow(2))
    }
}
