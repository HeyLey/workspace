package iga.workspace

import iga.workspace.auth.UserPrincipal
import iga.workspace.auth.UserSession
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.path
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.server.netty.EngineMain
import io.ktor.websocket.WebSockets
import iga.workspace.models.Mongo
import iga.workspace.models.mdb
import iga.workspace.models.mongo
import iga.workspace.models.sessions
import io.ktor.auth.Authentication
import io.ktor.auth.session
import io.ktor.response.respondRedirect
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import org.slf4j.event.Level
import java.time.Duration

/*
TODO:
- workspace scheme
- exceptions
- google oauth
- pretty ui
 */

fun main(args: Array<String>): Unit {
    // Connect to KMongo, resolving lazy.
    mdb.name
    EngineMain.main(args)
}

@KtorExperimentalLocationsAPI
fun Application.module() {
    install(Locations) {}

    install(Sessions) {
        cookie<UserSession>("GS_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(Authentication) {
        this.session<UserSession>() {
            validate {
                sessions.get<UserSession>()?.let { session ->
                    val key = mongo().sessions.get(session.sessionKey)
                    key?.let { UserPrincipal(it) }
                }
            }
            challenge {
                call.respondRedirect("/login")
            }
        }
    }

    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }

    install(AutoHeadResponse)

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(Mongo) {
        db = mdb
    }

    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    install(ContentNegotiation) {
        gson {}
    }

    routing {
        get("/health") {
            call.respond(HttpStatusCode.OK, "I'm alright!")
        }

        get("") {
            call.respondRedirect("/login")
        }
    }

}
