// Top-level build file where you can add configuration options common to all sub-projects/modules.
//import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.kotlin.dsl.*
buildscript {

    repositories {
        google()
        maven { url = uri("https://jitpack.io") }
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.GRADLE_BUILD_TOOLS}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
        classpath("android.arch.navigation:navigation-safe-args-gradle-plugin:${Versions.NAVIGATION}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}


tasks {
    val clean by registering(Delete::class)
    delete(rootProject.buildDir)
}



