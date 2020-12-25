package services

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.coroutines.MainScope

val scope = MainScope()
const val backendUrl = "http://localhost:8081"

val Request = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}