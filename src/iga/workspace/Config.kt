package iga.workspace

import com.google.gson.Gson
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileReader

data class Config(
    val serverName: String = "http://workspace:8080",
    val mongoConfig: MongoConfig = MongoConfig()
) {
    data class MongoConfig(
        val address: String = "localhost",
        val database: String = "workspace",
        val port: Int = 27017,
        val username: String? = null,
        val password: String? = null
    )
}

fun configFile(filename: String) = File(System.getenv("CONFIG_DIR") ?: ".", filename)

val configLogger = LoggerFactory.getLogger("Config")

inline fun <reified T> readConfig(filename: String, default: () -> T): T {
    val configFile = configFile(filename)
    configLogger.warn("Reading $configFile")
    val gson = Gson()
    return if (configFile.exists())
        gson.fromJson(FileReader(configFile), T::class.java)
    else
        default().also {
            configFile.writeText(gson.toJson(it))
        }

}

val cfg: Config by lazy {
    (readConfig("config.json") { Config() })
        .let {
            System.getenv("DB_HOST")?.let { value -> it.copy(mongoConfig = it.mongoConfig.copy(address = value)) }
                ?: it
        }
        .let {
            System.getenv("DB_PORT")?.let { value -> it.copy(mongoConfig = it.mongoConfig.copy(port = value.toInt())) }
                ?: it
        }
        .let {
            System.getenv("DB_NAME")?.let { value -> it.copy(mongoConfig = it.mongoConfig.copy(database = value)) }
                ?: it
        }
}