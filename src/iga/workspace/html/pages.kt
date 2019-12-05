package iga.workspace.html

import iga.workspace.auth.UserPrincipal
import iga.workspace.auth.UserSession
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receiveParameters
import io.ktor.response.respondRedirect
import io.ktor.routing.routing
import kotlinx.html.*
import iga.workspace.models.*
import io.ktor.auth.authenticate
import io.ktor.auth.principal
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import org.litote.kmongo.eq


@KtorExperimentalLocationsAPI
@Location("")
class WebPages {
    @Location(path = "/login")
    class Login()

    @Location("/workspace")
    class Workspace

    @Location(path = "/search")
    class Search
}

@KtorExperimentalLocationsAPI
fun Application.module() {

    routing {

        get<WebPages.Login> {
            call.sessions.get<UserSession>()?.let {
                mongo().sessions.get(it.sessionKey)?.let {
                    call.respondRedirect("/home")
                    return@get
                }
            }
            call.respondHtml {
                loginForm {}
            }
        }

        post<WebPages.Login> {
            val params = call.receiveParameters()
            runCatching {
                call.authenticateCredentials(params["login"] ?: "", params["password"] ?: "")
            }.onSuccess {
                call.respondRedirect("/home")
            }.onFailure {
                (it as? AuthenticationError)?.let {
                    call.respondHtml { loginForm { +it.msg } }
                }
            }
        }

        authenticate {

            route("/home") {
                get {
                    val admin = call.principal<UserPrincipal>()?.admin ?: error("Authenticate failed")
                    call.respondHtml {
                        head {}
                        body {
                            h2 {
                                +"Hello, "
                                +admin.login
                                +"!"

                                form {
                                    method = FormMethod.post
                                    action = "/logout"
                                    submitInput { value = "logout" }
                                }
                            }
                            div {
                                p {
                                    a(href = "/workspace") { +"Workspace" }
                                }
                                p {
                                    a(href = "/search") { +"Search" }
                                }
                            }
                        }
                    }
                }
            }

            post("/logout") {
                call.sessions.get<UserSession>()?.let {
                    mongo().sessions.collection.deleteOne(Session::uuid eq it.sessionKey)
                    call.sessions.set<UserSession>(null)
                    call.respondRedirect("/login")
                }
            }

            get<WebPages.Workspace> {
                val tables = mongo().tables.collection.find().toList()
                var pairs: MutableList<Pair<String, Boolean>> = mutableListOf(Pair("nothing", false))
                tables.forEach{pairs.add(Pair("Table " + it.name +": "+ it.employeeName, it.employeeName != ""))}
                call.respondHtml {
                    workspaceForm(pairs) {}
                }
            }

            post<WebPages.Workspace> {
                val params = call.receiveParameters()
                val paramsArray = params.toString().split(" ")
                val employeeName = params["employeeName"]?: ""
                val tableName = paramsArray[2].split("=")[0]?: ""
                runCatching {
                    call.takeTable(employeeName, tableName)
                }.onSuccess {
                    call.respondHtml {
                        addForm(employeeName, tableName){}
                    }
                }.onFailure {
                    // (it as? TableNotFoundError)?.let {
                    //     call.respondHtml { workspaceForm { +it.msg } }
                    //}
                }
            }

            get<WebPages.Search> {
                call.respondHtml {
                    searchForm{}
                }
            }

            post<WebPages.Search> {
                val params = call.receiveParameters()
                val employeeName = params["employeeName"] ?: ""
                val results = mongo().tables.searchEmployee(employeeName)
                var tables: MutableList<String> = mutableListOf()
                results.forEach{tables.add(it.name)}
                call.respondHtml {
                    searchForm{}
                    searchResults(employeeName, tables.toString()){}
                }

            }

        }
    }

}
