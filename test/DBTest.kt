package lambda.bigsister


import io.ktor.application.Application
import io.ktor.server.testing.TestApplicationCall
import io.ktor.server.testing.withTestApplication
import kotlinx.coroutines.runBlocking
import iga.workspace.models.*
import iga.workspace.module
import io.ktor.locations.KtorExperimentalLocationsAPI
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Test
import org.litote.kmongo.coroutine.CoroutineDatabase


@DisplayName("DB Tests")
class DBTest {

    private var dbVar: CoroutineDatabase? = null

    suspend fun createUser(db: CoroutineDatabase, login: String, password: String) =
        db.admins.collection.insertOne(
            Admin(
                login = login,
                password = password
            )
        )

    @BeforeEach
    @KtorExperimentalLocationsAPI
    fun initDb() {
        dbVar = runBlocking {
            initDatabase("workspace_test")
        }
    }

    @AfterEach
    fun cleanDb() {
        runBlocking {
            dbVar!!.drop()
        }
    }

    @DisplayName("Session creating")
    @Test
    @KtorExperimentalLocationsAPI
    fun testSession() {
        withTestApplication({ module() }) {
            val db = dbVar!!
            val sessions = db.sessions
            val admins = db.admins

            runBlocking(MongoDBScope(db)) {
                val login = "user"
                val password = "pwd"
                createUser(db, login, password)
                val admin = admins.byLogin(login)!!

                var sessionsList = sessions.collection.find().toList()

                assertThat(sessionsList.size, equalTo(0))

                val appCall = TestApplicationCall(Application(environment), false, this.coroutineContext)
                sessions.newSession(appCall, admin)

                sessionsList = sessions.collection.find().toList()

                assertThat(sessionsList.size, equalTo(1))
                assertThat(sessionsList[0].admin, equalTo(admin.id))

                val sessionUuid = sessionsList[0].uuid

                val getUser = sessions.get(sessionUuid)
                assertThat(getUser, equalTo(admin))

                /* sessions.deleteSession(appCall, user)
                assertNull(projects.collection.findOne(Session::uuid eq sessionUuid))
                */

            }
        }

    }

}