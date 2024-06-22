package ya.school.todoapp.presentation.ui.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object DateUtil {
    private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy")
    private val weekdayDateFormat = SimpleDateFormat("EE, MMMM dd")
    private val calendar = Calendar.getInstance()

    fun Date.toDateString(): String = simpleDateFormat.format(this)

    fun Date.toWeekDayDateString(): String = weekdayDateFormat.format(this)

    fun getYear(millis: Long): Int {
        calendar.timeInMillis = millis
        return calendar.get(Calendar.YEAR)
    }
}