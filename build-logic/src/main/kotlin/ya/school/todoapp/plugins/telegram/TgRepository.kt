package ya.school.todoapp.plugins.telegram

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.escapeIfNeeded
import java.io.File

class TgRepository(
    private val httpClient: HttpClient,
) {

    suspend fun upload(file: File, token: String, chatId: Int): HttpResponse {
        return httpClient.post("$BASE_URL/bot$token/sendDocument") {
            parameter("chat_id", chatId)
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("document", file.readBytes(), Headers.build {
                            append(HttpHeaders.ContentDisposition, "filename=${file.name.escapeIfNeeded()}")
                        })
                    }
                )
            )
        }
    }

    suspend fun sendMessage(message: String, token: String, chatId: Int): HttpResponse {
        return httpClient.post("$BASE_URL/bot$token/sendMessage") {
            parameter("chat_id", chatId)
            parameter("text", message)
        }
    }

    companion object {
        const val BASE_URL = "https://api.telegram.org"

        fun build(): TgRepository {
            return TgRepository(
                HttpClient(OkHttp) {
                    install(Logging) {
                        logger = Logger.DEFAULT
                        level = LogLevel.ALL
                    }
                }
            )
        }
    }
}