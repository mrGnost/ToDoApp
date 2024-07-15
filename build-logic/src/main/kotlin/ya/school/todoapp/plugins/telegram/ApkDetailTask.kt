package ya.school.todoapp.plugins.telegram

import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import javax.inject.Inject

abstract class ApkDetailTask @Inject constructor(
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
                val zip = ZipFile(it)
                var apkInfo = ""
                val sizes = zip.calculateSizes()
                sizes.entries.forEach { entry ->
                    apkInfo += toDetailed(entry.key, entry.value)
                }
                runBlocking {
                    tgRepository.sendMessage(
                        apkInfo,
                        token.get(),
                        chatId.get().toInt()
                    ).apply {
                        println("Status = $status")
                    }
                }
            }
    }

    private fun toDetailed(name: String, size: Double): String {
        return "- $name ${String.format("%.1f", size)} MB\n"
    }

    private fun ZipFile.calculateSizes(): Map<String, Double> {
        val sizes = mutableMapOf<String, Double>()
        entries().toList().forEach { entry ->
            val name = entry.name.split("/").first()
            sizes[name] = sizes.getOrDefault(name, .0) + entry.size.toDouble() / 1024 / 1024
        }
        return sizes
    }
}