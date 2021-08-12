package main.kotlin

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class BatchPinger(val urls: Collection<String>) {
    suspend fun execute(): List<ResponseData> {
        val response = mutableListOf<ResponseData>()
        for (url in urls) {
            val p = Pinger(url)
            p.execute()
            response.add(ResponseData(url, p.code ?: -1, p.latency ?: -1))
        }
        return response
    }
}