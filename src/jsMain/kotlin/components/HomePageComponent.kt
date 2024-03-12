package components

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.p

val HomePageComponent = FC<Props> {
    div {
        h1 {
            +"Home Page"
        }
        p {
            +"Welcome to the home page!"
        }
        p {
            +"This is a sample application built with Kotlin and React."
            +"It demonstrates how to use Kotlin and React together to build a simple web application."
            +"The application is built using the Kotlin/JS compiler and the React library."
            +"The application is designed to demonstrate how to use Kotlin and React together to build a simple web application."
        }
    }
}
