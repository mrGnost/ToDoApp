plugins {
    id("base-android-plugin")
    id("tg-plugin")
    alias(libs.plugins.hilt.android)
}

tg {
    token.set(providers.environmentVariable("TG_TOKEN"))
    chatId.set(providers.environmentVariable("TG_CHAT_ID"))
    validateSize.set(true)
    maxSizeMb.set(20)
    writeFullReport.set(true)
}

android {
    defaultConfig {
        applicationId = "ya.school.todoapp"
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    packaging {
        resources {
            excludes.addAll(
                listOf(
                    "/META-INF/{AL2.0,LGPL2.1}",
                    "META-INF/LICENSE.md",
                    "META-INF/LICENSE-notice.md"
                )
            )
        }
    }
}

dependencies {
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.work)
    androidTestImplementation(libs.hilt.testing)
}