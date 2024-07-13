package ya.school.todoapp.plugins.telegram

import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
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

    @get:Input
    abstract val apkVariant: Property<String>

    @get:Input
    abstract val apkVersionCode: Property<Int>

    @get:InputFile
    abstract val apkSizeMb: RegularFileProperty

    @TaskAction
    fun execute() {
        val apkText = apkSizeMb.get().let {
            it.asFile.readText().run {
                if (isNullOrEmpty())
                    ""
                else
                    "APK size: $this Mb"
            }
        } ?: ""
        apkDir.get().asFile.listFiles()
            ?.filter { it.name.endsWith(".apk") }
            ?.forEach {
                val newFile = File(it.parentFile, "todolist-${apkVariant.get()}-${apkVersionCode.get()}.apk")
                it.renameTo(newFile)
                runBlocking {
                    tgRepository.sendMessage(
                        "Build success. $apkText",
                        token.get(),
                        chatId.get().toInt()
                    ).apply {
                        println("Status = $status")
                    }
                    tgRepository.upload(newFile, token.get(), chatId.get().toInt()).apply {
                        println("Status = $status")
                    }
                }
            }
    }
}