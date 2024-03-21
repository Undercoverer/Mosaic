package gay.extremist.mosaic.bindings

import org.w3c.dom.HTMLVideoElement

@JsModule("dashjs")
@JsNonModule
external object dashjs {
    fun MediaPlayer(): MP
    interface MP {
        fun create(): MP
        fun initialize(video: HTMLVideoElement, url: String, autoPlay: Boolean)
    }
}