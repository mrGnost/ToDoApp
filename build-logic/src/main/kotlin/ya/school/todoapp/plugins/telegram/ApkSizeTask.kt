package ya.school.todoapp.plugins.telegram

import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class ApkSizeTask @Inject constructor(
    private val tgRepository: TgRepository
) : DefaultTask() {

    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @get:Input
    abstract val taskEnabled: Property<Boolean>

    @get:Input
    abstract val maxSizeMb: Property<Int>

    @get:OutputFile
    abstract val apkSizeMb: RegularFileProperty

    @TaskAction
    fun execute() {
        if (taskEnabled.getOrElse(false)) {
            apkDir.get().asFile.listFiles()
                ?.filter { it.name.endsWith(".apk") }
                ?.forEach {
                    val sizeMb = it.length() / 1024 / 1024
                    if (sizeMb > maxSizeMb.get()) {
                        runBlocking {
                            tgRepository.sendMessage(
                                "Build failed: APK file size $sizeMb MB is larger then max permitted $maxSizeMb MB",
                                token.get(),
                                chatId.get().toInt()
                            )
                        }
                        throw GradleException("APK file size $sizeMb MB is larger then max permitted $maxSizeMb MB")
                    }
                    apkSizeMb.get().asFile.writeText(sizeMb.toString())
                }
        } else {
            apkSizeMb.get().asFile.writeText("")
        }
    }
}