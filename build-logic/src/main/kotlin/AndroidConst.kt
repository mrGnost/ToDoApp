import org.gradle.api.JavaVersion

object AndroidConst {
    const val NAMESPACE = "ya.school.todoapp"
    const val COMPILE_SDK = 34
    const val MIN_SDK = 24
    val COMPILE_JDK_VERSION = JavaVersion.VERSION_17
    const val KOTLIN_JVM_TARGET = "17"
    const val KOTLIN_COMPILER_EXTENSION_VERSION = "1.5.14"
}