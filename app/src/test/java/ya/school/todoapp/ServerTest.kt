package ya.school.todoapp

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import ya.school.todoapp.ServerTestUtil.generateApi
import ya.school.todoapp.ServerTestUtil.setAddResponse
import ya.school.todoapp.ServerTestUtil.setListResponse
import ya.school.todoapp.ServerTestUtil.setTodoResponse
import ya.school.todoapp.data.network.TodoApi
import ya.school.todoapp.data.repository.NetworkRepositoryImpl
import ya.school.todoapp.domain.entity.Revisioned
import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoResult
import ya.school.todoapp.domain.repository.NetworkRepository
import java.util.Date

class ServerTest {
    private val webServer = MockWebServer()
    private val api: TodoApi by lazy { generateApi(webServer) }
    private val repository: NetworkRepository by lazy { NetworkRepositoryImpl(api) }

    @Before
    fun setup() {
        webServer.start(8080)
    }

    @Test
    fun `Get list of tasks on server without error`() {
        webServer.setListResponse(code = 200)
        runBlocking {
            try {
                val result = repository.getAllItems()
                assert(result is TodoResult.Success)
            } catch (e: Exception) {
                assert(false)
            }
        }
    }

    @Test
    fun `Get task by id from server and check id is the same`() {
        val mockId = "some_id"
        webServer.setTodoResponse(id = mockId, code = 200)
        runBlocking {
            try {
                val item = repository.getItem(mockId)
                assert(item is TodoResult.Success)
                assertEquals((item as TodoResult.Success).data.data.id, mockId)
            } catch (e: Exception) {
                assert(false)
            }
        }
    }

    @Test
    fun `Get task by id which doesn't exist`() {
        val mockId = "some_id"
        webServer.setTodoResponse(id = mockId, code = 404)
        runBlocking {
            try {
                val item = repository.getItem(mockId)
                assert(item is TodoResult.Error)
                assertEquals((item as TodoResult.Error).message, "Ошибка: элемент не найден")
            } catch (e: Exception) {
                assert(false)
            }
        }
    }

    @Test
    fun `Add new task successfully`() {
        val task = TodoItem("a", "a", TodoItem.Importance.Regular, isDone = false, createdAt = Date())
        val revisionedTask = Revisioned(task, 1)
        webServer.setAddResponse(id = task.id, code = 200)
        runBlocking {
            try {
                val item = repository.addItem(revisionedTask)
                assert(item is TodoResult.Success)
                assertEquals((item as TodoResult.Success).data.data.id, task.id)
            } catch (e: Exception) {
                assert(false)
            }
        }
    }

    @Test
    fun `Add new task when revision is different`() {
        val task = TodoItem("a", "a", TodoItem.Importance.Regular, isDone = false, createdAt = Date())
        val revisionedTask = Revisioned(task, 1)
        webServer.setAddResponse(id = task.id, code = 400)
        runBlocking {
            try {
                val item = repository.addItem(revisionedTask)
                assert(item is TodoResult.Error)
                assertEquals((item as TodoResult.Error).message, "Ошибка синхронизации данных")
            } catch (e: Exception) {
                assert(false)
            }
        }
    }

    @After
    fun shutdown() {
        webServer.shutdown()
    }
}