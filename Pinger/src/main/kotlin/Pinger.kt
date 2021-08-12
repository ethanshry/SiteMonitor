package main.kotlin

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking

class Pinger(val url: String, private val client: HttpClient = HttpClient()) {

    var latency: Long? = null
    var code: Int? = null

    fun execute() {
        val startTime = System.nanoTime()
        var endTime: Long
        try {
            val response: HttpResponse = runBlocking { client.get(url) }
            endTime = System.nanoTime()
            latency = endTime - startTime
            code = response.status.value
        } catch (e: Exception) {
            endTime = System.nanoTime()
            print("Exception caught while executing ping on $url: $e")
            code = -1
        }
        latency = endTime - startTime
    }
}