package ya.school.todoapp

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ya.school.todoapp.data.network.TodoApi

object ServerTestUtil {
    fun generateApi(mockWebServer: MockWebServer): TodoApi {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApi::class.java)
    }

    fun MockWebServer.setListResponse(code: Int = 200) {
        enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(getListResponse())
        )
    }

    fun MockWebServer.setTodoResponse(id: String, code: Int = 200) {
        enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(getTodoResponse(id))
        )
    }

    fun MockWebServer.setAddResponse(id: String, code: Int = 200) {
        enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(getNoRevisionResponse(id))
        )
    }

    private fun getListResponse() = """{
  "status": "ok",
  "list": [
    {
      "id": "someid",
      "text": "blablabla",
      "importance": "basic",
      "deadline": 1720240822867,
      "done": false,
      "color": "#FFFFFF",
      "created_at": 1720240822867,
      "changed_at": 1720240822867,
      "files": null,
      "last_updated_by": 1
    }
  ],
  "revision": 1
}""".trimIndent()

    private fun getTodoResponse(id: String) = """{
  "status": "ok",
  "element": {
    "id": "$id",
    "text": "blablabla",
    "importance": "basic",
    "deadline": 1720240822867,
    "done": false,
    "color": "#FFFFFF",
    "created_at": 1720240822867,
    "changed_at": 1720240822867,
    "files": null,
    "last_updated_by": 1
  },
  "revision": 1
}""".trimIndent()

    private fun getNoRevisionResponse(id: String) = """{
  "status": "ok",
  "element": {
    "id": "$id",
    "text": "blablabla",
    "importance": "basic",
    "deadline": 1720240822867,
    "done": false,
    "color": "#FFFFFF",
    "created_at": 1720240822867,
    "changed_at": 1720240822867,
    "files": null,
    "last_updated_by": 1
  }
}""".trimIndent()
}