package iga.workspace.models

import iga.workspace.auth.UserSession
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.eq
import org.litote.kmongo.newId

typealias Password = String
typealias Username = String

class AuthenticationError(
    val msg: String,
    val code: HttpStatusCode
) : RuntimeException(msg) {
    suspend fun endCall(call: ApplicationCall) = call.respond(code, msg)
}

/** Authenticates or rejects user. Throws AuthenticationError */
suspend fun ApplicationCall.authenticateCredentials(login: String, password: String) {
    val admin = mongo().admins.byLogin(login) ?: throw AuthenticationError(
        "No user with that login found",
        HttpStatusCode.Forbidden
    )
    if (password != admin.password) throw AuthenticationError (
        "Wrong password",
        HttpStatusCode.Forbidden
    )
    val session = mongo().sessions.newSession(this, admin)

    sessions.set(UserSession(session.uuid))
}

data class Admin(
    val login: Username,
    val password: Password = "",
    @BsonId
    val id: Id<Admin> = newId()
)

val MongoDB.admins get() = AdminOps(this)

class AdminOps(private val db: MongoDB) {
    val collection get() = db.getCollection<Admin>("admins")
    suspend fun byLogin(username: Username): Admin? = collection.find(Admin::login eq username).first()
    suspend fun init() {
        collection.insertOne(Admin("root", "root"))
    }
}