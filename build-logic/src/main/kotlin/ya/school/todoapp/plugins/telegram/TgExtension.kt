package ya.school.todoapp.plugins.telegram

import org.gradle.api.provider.Property

interface TgExtension {
    val token: Property<String>
    val chatId: Property<String>
    val validateSize: Property<Boolean>
    val maxSizeMb: Property<Int>
}