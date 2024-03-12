package components

import js.objects.jso
import react.create
import react.router.dom.createBrowserRouter

val browserRouter = createBrowserRouter(
    routes = arrayOf(
        jso {
            path = "/"
            element = HomePageComponent.create()
            errorElement = ErrorComponent.create {
                message = "Could not find the ${this@jso.path} page"
            }
        },

        jso {
            path = "/about"
            element = AboutPageComponent.create()
            errorElement = ErrorComponent.create {
                message = "Could not find the ${this@jso.path} page"
            }
        },

        jso {
            path = "/video"
            element = VideoPlayerComponent.create {
                src = "https://dash.akamaized.net/envivio/EnvivioDash3/manifest.mpd"
            }
            errorElement = ErrorComponent.create {
                message = "Could not find the ${this@jso.path} page"
            }
        }
    )
)