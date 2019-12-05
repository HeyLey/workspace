package iga.workspace.models

import io.ktor.application.ApplicationCall
import io.ktor.request.userAgent
import org.litote.kmongo.Id
import org.litote.kmongo.eq
import java.util.*

data class Session(
    val admin: Id<Admin>,
//    val started: DateTime,
    val userAgent: String,
    val uuid: String = UUID.randomUUID().toString()
)

val MongoDB.sessions get() = SessionOps(this)

class SessionOps(private val db: MongoDB) {
    val collection get() = db.getCollection<Session>("sessions")

    suspend fun newSession(call: ApplicationCall, admin: Admin) = Session(
        admin.id,
//        DateTime.now(),
        userAgent = call.request.userAgent() ?: "Unknown"
    ).let {
        collection.save(it)
        it
    }

    suspend fun get(uuid: String) : Admin? =
        collection.find(Session::uuid eq uuid).first()?.let {
            db.admins.collection.findOne(Admin::id eq it.admin)
        }

    //suspend fun deleteSession(call: ApplicationCall, user: User) { }

}