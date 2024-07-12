package ya.school.todoapp.plugins.telegram

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized

class TgPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val androidComponents = target.extensions.findByType(AndroidComponentsExtension::class.java)
            ?: throw GradleException("android plugin required.")

        val ext = target.extensions.create("tg", TgExtension::class.java)

        val repository = TgRepository(
            HttpClient(OkHttp) {
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                }
            }
        )

        androidComponents.onVariants { variant ->
            println("Variant: ${variant.name}")
            val artifacts = variant.artifacts.get(SingleArtifact.APK)
            target.tasks.register(
                "tgReportFor${variant.name.capitalized()}",
                TgTask::class.java,
                repository
            ).configure {
                apkDir.set(artifacts)
                token.set(ext.token)
                chatId.set(ext.chatId)
            }
        }
    }
}