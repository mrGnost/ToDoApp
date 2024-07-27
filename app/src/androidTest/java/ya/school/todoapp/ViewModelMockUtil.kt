package ya.school.todoapp

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.presentation.ui.home.HomeViewModel
import ya.school.todoapp.presentation.ui.task.TaskFormViewModel
import java.util.Date

object ViewModelMockUtil {
    fun getHomeViewModel(): HomeViewModel {
        return mockk {
            every { todoItemsFlow } returns flowOf(
                listOf(TodoItem("a", "a", TodoItem.Importance.Regular, isDone = false, createdAt = Date()))
            )
            every { snackBarMessage } returns null
            every { changeItemCheck(any(), any()) } returns Unit
            every { finish() } returns Unit
        }
    }

    fun getTaskViewModel(): TaskFormViewModel {
        return mockk {
            every { getItem(any()) } returns Unit
            every { runBlocking { saveItem(any()) } } returns true

        }
    }
}