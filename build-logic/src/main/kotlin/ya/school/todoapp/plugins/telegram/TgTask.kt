package ya.school.todoapp.plugins.telegram

import io.ktor.client.call.body
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class TgTask @Inject constructor(
    private val tgRepository: TgRepository
) : DefaultTask() {

    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @TaskAction
    fun execute() {
        apkDir.get().asFile.listFiles()
            ?.filter { it.name.endsWith(".apk") }
            ?.forEach {
                runBlocking {
                    tgRepository.sendMessage("Build success", token.get(), chatId.get().toInt()).apply {
                        println("Status = ${body<String>()}")
                    }
                    tgRepository.upload(it, token.get(), chatId.get().toInt()).apply {
                        println("Status = ${body<String>()}")
                    }
                }
            }
    }
}