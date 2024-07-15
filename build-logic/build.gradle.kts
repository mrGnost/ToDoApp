plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

gradlePlugin {
    plugins.register("tgPlugin") {
        id = "tg-plugin"
        implementationClass = "ya.school.todoapp.plugins.telegram.TgPlugin"
    }
}

dependencies {
    implementation(libs.agp)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.okhttp)
    implementation(libs.ktor.logging)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}