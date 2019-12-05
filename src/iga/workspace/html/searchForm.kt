package iga.workspace.html

import kotlinx.html.*

fun HTML.searchForm(message: P.() -> Unit) {
    head {
    }
    body {
        h2 { +"Search" }
        p {
            a(href = "/home") { +"← Back to home page" }
            form {
                method = FormMethod.post
                action = "/logout"
                submitInput { value = "logout" }
            }
        }
        p { +"To search: 1) Enter first and last name of person; 2) Press 'Search'." }

        form {
            method = FormMethod.post
            encType = FormEncType.applicationXWwwFormUrlEncoded
            action = "/search"
            p {
                message()
            }
            textInput {
                name = "employeeName"
                placeholder = "Ivan Ivanov"
            }
            submitInput {
                value = "Search"
            }

        }

    }
}