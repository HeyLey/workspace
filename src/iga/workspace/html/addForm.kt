package iga.workspace.html

import kotlinx.html.*

fun HTML.addForm(employeeName: String, tableName: String, message: DIV.() -> Unit) {
    head { }
    body {
        h1 { +"Table ${tableName}: ${employeeName}" }
        p {
            a(href = "/workspace") { +"← Back to workspace page" }
        }
    }
}