package test.kotlin

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*

import main.kotlin.Pinger
import kotlin.test.*

class PingerTest {
    private var client: HttpClient = HttpClient(MockEngine) {
        engine {
            addHandler { request ->
                when (request.url.toString()) {
                    "http://www.google.com/" -> {
                        respond("OK", HttpStatusCode.OK)
                    }
                    else -> error("Unhandled ${request.url}")
                }
            }
        }
    }

    @Test
    fun testPingerWritesValues() {
        val pinger = Pinger("http://www.google.com", client)
        assertTrue(pinger.latency == null)
        assertTrue(pinger.code == null)
        pinger.execute()
        assertFalse(pinger.latency == null)
        assertFalse(pinger.code == null)
    }

    @Test
    fun testSiteOK() {
        val pinger = Pinger("http://www.google.com", client)
        pinger.execute()
        assertEquals(pinger.code, HttpStatusCode.OK.value, "Response Code comes back as expected")
    }

    @Test
    fun testSiteNotFound() {
        val pinger = Pinger("http://www.bing.com", client)
        pinger.execute()
        assertEquals(-1, pinger.code)
        print(pinger.latency)
        assertTrue(pinger.latency != null)
    }
}