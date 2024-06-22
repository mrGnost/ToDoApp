package ya.school.todoapp.presentation.ui.util

import java.text.SimpleDateFormat
import java.util.Date

object DateUtil {
    private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy")

    fun Date.toDateString(): String = simpleDateFormat.format(this)
}