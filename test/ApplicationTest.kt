package lambda.bigsister

import iga.workspace.module
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @KtorExperimentalAPI
    @KtorExperimentalLocationsAPI
    @Test
    fun testResponse() {
        withTestApplication({
            module()
        }) {
            handleRequest(HttpMethod.Get, "/health").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}
