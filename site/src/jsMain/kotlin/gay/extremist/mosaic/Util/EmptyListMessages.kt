package gay.extremist.mosaic.Util

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.text.SpanText
import gay.extremist.mosaic.data_models.VideoDisplayResponse
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.percent

@Composable
fun ifVideosEmpty(videos: List<VideoDisplayResponse>, text: String){
    if(videos.isEmpty()){
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(100.percent)
                .height(20.percent)
                .verticalAlign(VerticalAlign.Top)
                .textAlign(TextAlign.Center)
        ){
            SpanText(text,
                Modifier.fontWeight(FontWeight.Bolder)
                    .fontSize(FontSize.XXLarge)
                    .opacity(50.percent)
            )
        }
    }
}