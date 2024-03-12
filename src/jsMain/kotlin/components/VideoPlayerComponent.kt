package components

import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.useEffectOnce
import react.useRef
import utils.dash.dashjs
import web.html.HTMLVideoElement

external interface DashVideoPlayerProps : Props {
    var src: String
}

val VideoPlayerComponent = FC<DashVideoPlayerProps> { props ->
    val videoRef = useRef<HTMLVideoElement>()

    useEffectOnce {
        val player = dashjs.MediaPlayer().create()
        videoRef.current?.let { player.initialize(it, props.src, true) }
    }

    return@FC ReactHTML.video {
        controls = true
        ref = videoRef
    }
}