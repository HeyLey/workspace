package iga.workspace.html

import kotlinx.html.*

fun HTML.searchResults(name: String, results: String, message: DIV.() -> Unit) {
    head { }
    body {
        h2 { +"Results" }
        div {
            +"Your search: ${name}"
            p {
                +"Table: ${results}"
            }
        }

    }
}