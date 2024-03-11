package utils.dash

import web.html.HTMLVideoElement

@JsModule("dashjs")
@JsNonModule
external object dashjs {
    fun MediaPlayer(): MP

    interface MP {
        fun create(): MP
        fun initialize(video: HTMLVideoElement, url: String, autoPlay: Boolean)
    }
}