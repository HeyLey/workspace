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

            p {}

            var i = 1
            val standart_table =
                " width:39px; height:39px; table-cell; text-align: center; border-width: 5px; border-color: black black; "
            val table_margin = " margin-left: 39px; margin-top: -39px; "
            val table_margin2 = " margin-left: 78px; margin-top: -39px; "


            // Hall 1, Line 1: 1-20
            div {
                for (k in 1..5) {
                    for (j in 1..2) {
                        div {
                            title = pairs[i].first
                            style = if (pairs[i].second) {
                                "background: red; ${standart_table}"
                            } else {
                                "background: green; ${standart_table}"
                            }
                            checkBoxInput {
                                name = i.toString()
                                classes = setOf("checkbox")
                            }
                            br { +"[${i}]" }
                        }
                        i++
                        div {
                            title = pairs[i].first
                            style = if (pairs[i].second) {
                                "background: red; ${standart_table} ${table_margin}"
                            } else {
                                "background: green; ${standart_table} ${table_margin}"
                            }
                            checkBoxInput {
                                name = i.toString()
                                classes = setOf("checkbox")
                            }
                            br { +"[${i}]" }
                        }
                        i++
                    }
                    p {}
                }
            }

            //Hall 1, Line 2: 21-34
            div {
                style = "margin-top: -471px; margin-left: 110px;"
                for (j in 1..2) {
                    div {
                        title = pairs[i].first
                        style = if (pairs[i].second) {
                            "background: red; ${standart_table}"
                        } else {
                            "background: green; ${standart_table}"
                        }
                        checkBoxInput {
                            name = i.toString()
                            classes = setOf("checkbox")
                        }
                        br { +"[${i}]" }
                    }
                    i++
                }
                p {}

                for (k in 1..2) {
                    for (j in 1..2) {
                        div {
                            title = pairs[i].first
                            style = if (pairs[i].second) {
                                "background: red; ${standart_table}"
                            } else {
                                "background: green; ${standart_table}"
                            }
                            checkBoxInput {
                                name = i.toString()
                                classes = setOf("checkbox")
                            }
                            br { +"[${i}]" }
                        }
                        i++
                        div {
                            title = pairs[i].first
                            style = if (pairs[i].second) {
                                "background: red; ${standart_table} ${table_margin}"
                            } else {
                                "background: green; ${standart_table} ${table_margin}"
                            }
                            checkBoxInput {
                                name = i.toString()
                                classes = setOf("checkbox")
                            }
                            br { +"[${i}]" }
                        }
                        i++
                    }
                    p {}
                }

                for (k in 1..2) {
                    for (j in 1..2) {
                        div {
                            title = pairs[i].first
                            style = if (pairs[i].second) {
                                "background: red; ${standart_table}"
                            } else {
                                "background: green; ${standart_table}"
                            }
                            checkBoxInput {
                                name = i.toString()
                                classes = setOf("checkbox")
                            }
                            br { +"[${i}]" }
                        }
                        i++
                    }
                    p {}
                }

            }

            // Vertical line
            div {
                style = "margin-top: -471px; margin-left: 220px; border-left: 1px solid grey; height: 353px;"
            }

            //Hall 1, Line 3: 35-41
            div {
                style = "margin-top: -352px; margin-left: 250px;"

                div {
                    title = pairs[i].first
                    style = if (pairs[i].second) {
                        "background: red; ${standart_table}"
                    } else {
                        "background: green; ${standart_table}"
                    }
                    checkBoxInput {
                        name = i.toString()
                        classes = setOf("checkbox")
                    }
                    br { +"[${i}]" }
                }
                i++
                div {
                    title = pairs[i].first
                    style = if (pairs[i].second) {
                        "background: red; ${standart_table} ${table_margin}"
                    } else {
                        "background: green; ${standart_table} ${table_margin}"
                    }
                    checkBoxInput {
                        name = i.toString()
                        classes = setOf("checkbox")
                    }
                    br { +"[${i}]" }
                }
                i++

                p {}

                div {
                    style = "margin-top: 42px; margin-left: -30px; width: 160px;"
                    hr {
                    }
                }

                for (k in 1..2) {
                    for (j in 1..2) {
                        div {
                            title = pairs[i].first
                            style = if (pairs[i].second) {
                                "background: red; ${standart_table}"
                            } else {
                                "background: green; ${standart_table}"
                            }
                            checkBoxInput {
                                name = i.toString()
                                classes = setOf("checkbox")
                            }
                            br { +"[${i}]" }
                        }
                        i++
                    }
                    p {}
                    if (k == 1) {
                        div {
                            style = "margin-top: 20px; margin-left: -30px; width: 160px;"
                            hr {
                            }
                        }
                    }

                }

                div {
                    title = pairs[i].first
                    style = if (pairs[i].second) {
                        "background: red; ${standart_table}"
                    } else {
                        "background: green; ${standart_table}"
                    }
                    checkBoxInput {
                        name = i.toString()
                        classes = setOf("checkbox")
                    }
                    br { +"[${i}]" }
                }
                i++

                div {
                    style = "margin-top: 18px; margin-left: -30px; width: 160px;"
                    hr {
                    }
                }
            }

            //Hall 1, Line 4: 42-45
            div {
                style = "margin-top: -157px; margin-left: 320px;"
                div {
                    title = pairs[i].first
                    style = if (pairs[i].second) {
                        "background: red; ${standart_table}"
                    } else {
                        "background: green; ${standart_table}"
                    }
                    checkBoxInput {
                        name = i.toString()
                        classes = setOf("checkbox")
                    }
                    br { +"[${i}]" }
                }
                i++

                p {}

                for (j in 1..2) {
                    div {
                        title = pairs[i].first
                        style = if (pairs[i].second) {
                            "background: red; ${standart_table}"
                        } else {
                            "background: green; ${standart_table}"
                        }
                        checkBoxInput {
                            name = i.toString()
                            classes = setOf("checkbox")
                        }
                        br { +"[${i}]" }
                    }
                    i++
                }

                p {}
                div {
                    style = "margin-top: 20px; "
                }
                p {}

                div {
                    title = pairs[i].first
                    style = if (pairs[i].second) {
                        "background: red; ${standart_table}"
                    } else {
                        "background: green; ${standart_table}"
                    }
                    checkBoxInput {
                        name = i.toString()
                        classes = setOf("checkbox")
                    }
                    br { +"[${i}]" }
                }
                i++
            }

            p {}

            //Hall 2
            div {
                style = "margin-top: 80px"
                //Hall 2, Row 1: 46-51
                div {
                    for (j in 1..2) {
                        div {
                            title = pairs[i].first
                            style = if (pairs[i].second) {
                                "background: red; ${standart_table}"
                            } else {
                                "background: green; ${standart_table}"
                            }
                            checkBoxInput {
                                name = i.toString()
                                classes = setOf("checkbox")
                            }
                            br { +"[${i}]" }
                        }
                        i++
                        div {
                            title = pairs[i].first
                            style = if (pairs[i].second) {
                                "background: red; ${standart_table} ${table_margin}"
                            } else {
                                "background: green; ${standart_table} ${table_margin}"
                            }
                            checkBoxInput {
                                name = i.toString()
                                classes = setOf("checkbox")
                            }
                            br { +"[${i}]" }
                        }
                        i++
                        div {
                            title = pairs[i].first
                            style = if (pairs[i].second) {
                                "background: red; ${standart_table} ${table_margin2}"
                            } else {
                                "background: green; ${standart_table} ${table_margin2}"
                            }
                            checkBoxInput {
                                name = i.toString()
                                classes = setOf("checkbox")
                            }
                            br { +"[${i}]" }
                        }
                        i++
                    }
                    p {}
                }

                //Hall 2, Row 2: 52-59
                div {
                    style = "margin-top=100px;"
                    div {
                        for (j in 1..2) {
                            div {
                                title = pairs[i].first
                                style = if (pairs[i].second) {
                                    "background: red; ${standart_table}"
                                } else {
                                    "background: green; ${standart_table}"
                                }
                                checkBoxInput {
                                    name = i.toString()
                                    classes = setOf("checkbox")
                                }
                                br { +"[${i}]" }
                            }
                            i++
                        }
                    }
                    div {
                        style = "margin-left: 65px; margin-top: -79px;"
                        for (j in 1..2) {
                            div {
                                title = pairs[i].first
                                style = if (pairs[i].second) {
                                    "background: red; ${standart_table}"
                                } else {
                                    "background: green; ${standart_table}"
                                }
                                checkBoxInput {
                                    name = i.toString()
                                    classes = setOf("checkbox")
                                }
                                br { +"[${i}]" }
                            }
                            i++
                            div {
                                title = pairs[i].first
                                style = if (pairs[i].second) {
                                    "background: red; ${standart_table} ${table_margin}"
                                } else {
                                    "background: green; ${standart_table} ${table_margin}"
                                }
                                checkBoxInput {
                                    name = i.toString()
                                    classes = setOf("checkbox")
                                }
                                br { +"[${i}]" }
                            }
                            i++
                            div {
                                title = pairs[i].first
                                style = if (pairs[i].second) {
                                    "background: red; ${standart_table} ${table_margin2}"
                                } else {
                                    "background: green; ${standart_table} ${table_margin2}"
                                }
                                checkBoxInput {
                                    name = i.toString()
                                    classes = setOf("checkbox")
                                }
                                br { +"[${i}]" }
                            }
                            i++
                        }
                        p {}
                    }

                }

                //Hall 2, Row 3: 60-62
                div {
                    style = "margin-top=100px;"
                    div {
                        title = pairs[i].first
                        style = if (pairs[i].second) {
                            "background: red; ${standart_table}"
                        } else {
                            "background: green; ${standart_table}"
                        }
                        checkBoxInput {
                            name = i.toString()
                            classes = setOf("checkbox")
                        }
                        br { +"[${i}]" }
                    }
                    i++
                    div {
                        title = pairs[i].first
                        style = if (pairs[i].second) {
                            "background: red; ${standart_table} ${table_margin}"
                        } else {
                            "background: green; ${standart_table} ${table_margin}"
                        }
                        checkBoxInput {
                            name = i.toString()
                            classes = setOf("checkbox")
                        }
                        br { +"[${i}]" }
                    }
                    i++
                    div {
                        title = pairs[i].first
                        style = if (pairs[i].second) {
                            "background: red; ${standart_table} ${table_margin2}"
                        } else {
                            "background: green; ${standart_table} ${table_margin2}"
                        }
                        checkBoxInput {
                            name = i.toString()
                            classes = setOf("checkbox")
                        }
                        br { +"[${i}]" }
                    }
                    i++
                }

                p {}
            }

            div {
                style = "margin-left: 220px; margin-top: -149px;"

                //Hall 2, Line 1: 63-70
                div {
                    for (j in 1..4) {
                        div {
                            title = pairs[i].first
                            style = if (pairs[i].second) {
                                "background: red; ${standart_table}"
                            } else {
                                "background: green; ${standart_table}"
                            }
                            checkBoxInput {
                                name = i.toString()
                                classes = setOf("checkbox")
                            }
                            br { +"[${i}]" }
                        }
                        i++
                        div {
                            title = pairs[i].first
                            style = if (pairs[i].second) {
                                "background: red; ${standart_table} ${table_margin}"
                            } else {
                                "background: green; ${standart_table} ${table_margin}"
                            }
                            checkBoxInput {
                                name = i.toString()
                                classes = setOf("checkbox")
                            }
                            br { +"[${i}]" }
                        }
                        i++
                    }
                    p {}
                }

                //Hall 2, Line 2: 71
                div {
                    style = "margin-left: 230px; margin-top: -230px;"
                    div {
                        title = pairs[i].first
                        style = if (pairs[i].second) {
                            "background: red; ${standart_table} ${table_margin}"
                        } else {
                            "background: green; ${standart_table} ${table_margin}"
                        }
                        checkBoxInput {
                            name = i.toString()
                            classes = setOf("checkbox")
                        }
                        br { +"[${i}]" }
                    }
                    i++
                }
            }

        }

        div {
            style="height: 190px;"
        }

    }

}
