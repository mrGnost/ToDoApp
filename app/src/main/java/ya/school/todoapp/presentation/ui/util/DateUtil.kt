package ya.school.todoapp.presentation.ui.util

import java.text.SimpleDateFormat
import java.util.Date

object DateUtil {
    val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy")

    fun Date.toDateString() = simpleDateFormat.format(this)
}