package components

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.ul

val AboutPageComponent = FC<Props> {
    div {
        h1 {
            +"About"
        }
        p {
            +"This is the about page"
        }
        p {
            +"Here is some info about the project:"
        }
        ul {
            li {
                +"Version 1.0"
            }
            li {
                +"Author: John Doe"
            }
            li {
                +"Date: 2023-05-01"
            }
            li {
                +"Description: This is a sample project to demonstrate the use of React and Kotlin"
            }
        }
    }
}
