package ya.school.todoapp.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ya.school.todoapp.data.network.entity.TodoItemRequestDTO
import ya.school.todoapp.data.network.entity.TodoItemResponseDTO
import ya.school.todoapp.data.network.entity.TodoListRequestDTO
import ya.school.todoapp.data.network.entity.TodoListResponseDTO

/**
 * Интерфейс, описывающий API бэкенда
 */
interface TodoApi {
    @GET("list")
    suspend fun getAllItems(): Response<TodoListResponseDTO>

    @GET("list/{id}")
    suspend fun getItem(@Path("id") id: String): Response<TodoItemResponseDTO>

    @PATCH("list")
    suspend fun updateList(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body list: TodoListRequestDTO
    ): Response<TodoListResponseDTO>

    @POST("list")
    suspend fun addItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body item: TodoItemRequestDTO
    ): Response<TodoItemResponseDTO>

    @PUT("list/{id}")
    suspend fun changeItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String,
        @Body item: TodoItemRequestDTO
    ): Response<TodoItemResponseDTO>

    @DELETE("list/{id}")
    suspend fun deleteItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String
    ): Response<TodoItemResponseDTO>
}