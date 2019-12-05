package iga.workspace.html

import kotlinx.html.*

fun HTML.loginForm(message: P.() -> Unit) {
    head { }
    body {
        h1 { +"Login" }
        form {
            method = FormMethod.post
            encType = FormEncType.applicationXWwwFormUrlEncoded
            action = "/login"
            p {
                message()
            }
            textInput {
                name = "login"
            }
            textInput {
                type = InputType.password
                name = "password"
                classes = setOf("password")
            }
            submitInput {
                value = "Login"
            }

        }
    }
}