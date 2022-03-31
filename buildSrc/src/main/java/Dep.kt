object Dep {

    object Gradle {
        const val android = "com.android.tools.build:gradle:${Ver.androidGradle}"
        const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Ver.kotlin}"
    }

    object Compose {
        const val ui = "androidx.compose.ui:ui:${Ver.compose}"
        const val test = "androidx.compose.ui:ui-test-junit4:${Ver.compose}"
        const val material = "androidx.compose.material:material:${Ver.compose}"

        object Accompanist {
            const val inset = "com.google.accompanist:accompanist-insets:${Ver.accompanist}"
        }

        object Misc {
            const val material3 = "androidx.compose.material3:material3:1.0.0-alpha01"
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended:1.1.0-alpha01"
        }
    }
}