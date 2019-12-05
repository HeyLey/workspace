package iga.workspace.models

import com.mongodb.ConnectionString
import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.ApplicationFeature
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.util.AttributeKey
import io.ktor.util.pipeline.PipelinePhase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import iga.workspace.cfg
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.reactivestreams.KMongo
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

typealias MongoDB = CoroutineDatabase
const val N = 71

suspend fun initDatabase(name: String) =
    KMongo
        .createClient(ConnectionString(
            URLBuilder(
                protocol = URLProtocol.createOrDefault("mongodb"),
                host = cfg.mongoConfig.address,
                port = cfg.mongoConfig.port,
                user = cfg.mongoConfig.username,
                password = cfg.mongoConfig.password
            ).buildString()
        ))
        .getDatabase(name)
        .let { a -> CoroutineDatabase(a) }
        .apply {
            admins.init()
            for (i in 1..N) {
                tables.addTable(i.toString())
            }

        }

val mdb: CoroutineDatabase by lazy { runBlocking {
    initDatabase(
        cfg.mongoConfig.database
    )
} }

class MongoConfig() {
    lateinit var db: MongoDB
}

/** Injects [MongoDBScope] into KTor Application Pipeline coroutine context. */
class Mongo {
    companion object Feature : ApplicationFeature<Application, MongoConfig, Mongo> {
        override val key: AttributeKey<Mongo>
            get() = AttributeKey<Mongo>("MongoDB context injector")

        override fun install(pipeline: Application, configure: MongoConfig.() -> Unit): Mongo {
            val mongoPhase = PipelinePhase("Mongo injector")

            pipeline.insertPhaseAfter(ApplicationCallPipeline.Features, mongoPhase)

            pipeline.intercept(mongoPhase) {
                withContext(
                    MongoDBScope(
                        MongoConfig().apply(
                            configure
                        ).db
                    )
                ) {
                    proceed()
                }
            }

            return Mongo()
        }

    }
}

class MongoDBScope(val mongoDB: MongoDB) : CoroutineContext.Element {
    companion object Key : CoroutineContext.Key<MongoDBScope>

    override val key: CoroutineContext.Key<*> get() = Key
}

suspend fun mongo() =
    coroutineContext[MongoDBScope]?.mongoDB ?: throw RuntimeException("No MongoDB found in current coroutine context.")