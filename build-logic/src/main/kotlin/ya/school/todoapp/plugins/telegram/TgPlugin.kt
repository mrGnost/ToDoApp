package ya.school.todoapp.plugins.telegram

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.BuildConfigField
import com.android.build.api.variant.Variant
import com.android.build.gradle.internal.tasks.factory.dependsOn
import gradle.kotlin.dsl.accessors._ffe51ee91362977ddc98906f5440a003.android
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.configurationcache.extensions.capitalized
import java.io.File

class TgPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val androidComponents = target.extensions.findByType(AndroidComponentsExtension::class.java)
            ?: throw GradleException("android plugin required.")

        val ext = target.extensions.create("tg", TgExtension::class.java)

        val repository = TgRepository.build()

        androidComponents.onVariants { variant ->
            println("Variant: ${variant.name}")
            println("Code: ${target.android.defaultConfig.versionCode}")
            val tgReportTask = target.configureTgReportTask(ext, variant, repository)
            val apkSizeTask = target.configureApkSizeTask(ext, variant, repository)
            tgReportTask.configure {
                apkSizeMb.set(apkSizeTask.get().apkSizeMb)
            }
            tgReportTask.dependsOn(apkSizeTask)
        }
    }

    private fun Project.configureTgReportTask(
        extension: TgExtension,
        variant: Variant,
        repository: TgRepository
    ): TaskProvider<TgTask> {
        return tasks.register(
            "tgReportFor${variant.name.capitalized()}",
            TgTask::class.java,
            repository
        ).apply {
            configure {
                apkDir.set(variant.artifacts.get(SingleArtifact.APK))
                token.set(extension.token)
                chatId.set(extension.chatId)
                apkVariant.set(variant.name)
                apkVersionCode.set(android.defaultConfig.versionCode)
            }
        }
    }

    private fun Project.configureApkSizeTask(
        extension: TgExtension,
        variant: Variant,
        repository: TgRepository
    ): TaskProvider<ApkSizeTask> {
        return tasks.register(
            "validateApkSizeFor${variant.name.capitalized()}",
            ApkSizeTask::class.java,
            repository
        ).apply {
            configure {
                apkDir.set(variant.artifacts.get(SingleArtifact.APK))
                token.set(extension.token)
                chatId.set(extension.chatId)
                maxSizeMb.set(extension.maxSizeMb)
                taskEnabled.set(extension.validateSize)
                apkSizeMb.set(File("apk_size.txt"))
            }
        }
    }
}