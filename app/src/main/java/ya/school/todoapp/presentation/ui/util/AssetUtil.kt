package ya.school.todoapp.presentation.ui.util

import android.content.Context

object AssetUtil {
    fun Context.readJson(path: String) = applicationContext.assets
        .open(path)
        .bufferedReader().use {
            it.readText()
        }
}