package iga.workspace.html

import iga.workspace.models.N
import kotlinx.html.*

fun HTML.workspaceForm(pairs: List<Pair<String, Boolean>>, message: DIV.() -> Unit) {
    head {
        script(type = ScriptType.textJavaScript) {
            src = "https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"
        }

        script(type = ScriptType.textJavaScript) {
            unsafe {
                raw("""
                    $(function() {
                        $("input:checkbox").on('click', function() {
                            var box = $(this);
                            if (box.is(":checked")) {
                                $("input:checkbox").prop("checked", false);
                                box.prop("checked", true);
                            } else {
                                box.prop("checked", false);
                            }
                        });
                    });
                """
                )
            }
        }
    }
    body {
        h2 {
            +"Workspace"
        }
        p {
            a(href = "/home") { +"← Back to home page" }
            form {
                method = FormMethod.post
                action = "/logout"
                submitInput { value = "logout" }
            }
        }
        p { +"To get table: 1) Enter your first and last name; 2) Select table; 3) Press 'Save'." }
        p { +"If you want to delete a person from the table: 1) Select table; 2) Press 'Clear'." }
        p { +"Note: onMouseOver to show 'Who is sitting here?'" }

        form {
            method = FormMethod.post
            action = "/workspace"

            textInput {
                name = "employeeName"
                placeholder = "Ivan Ivanov"
            }

            submitInput {
                value = "Save"
            }

            submitInput {
                value = "Clear"
            }

            for (i in 1..N) {
                p {}

                div {
                    title = pairs[i].first
                    style = if (pairs[i].second) {
                        "background: red; width:39px; height:39px; table-cell; text-align: center;"
                    } else {
                        "background: green; width:39px; height:39px; table-cell; text-align: center;"
                    }
                    checkBoxInput {
                        name = i.toString()
                        classes = setOf("checkbox")
                    }
                    br { +"[${i}]" }
                }
            }

        }

    }

}