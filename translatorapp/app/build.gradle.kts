
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("org.jetbrains.kotlin.android.extensions")
    id("kotlin-android")
}

android {
    compileSdkVersion(Versions.COMPILE_SDK)
    buildToolsVersion(Versions.BUILD_TOOLS)

    defaultConfig {
        applicationId = "com.example.translatorapp"
        minSdkVersion(Versions.MIN_SDK)
        targetSdkVersion(Versions.TARGET_SDK)
        versionCode = Versions.APP_VERSION
        versionName = Versions.APP_VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
         getByName("release") {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        val options = this
        options.jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
    }

}

dependencies {

    implementation(project(":core"))

    // Kotlin
    implementation(Dependency.KOTLIN_STDLIB_JDK7)
    implementation(Dependency.KOTLIN_STDLIB)


    // UI
    implementation(Dependency.CONSTRAINT_LAYOUT)
    implementation(Dependency.MATERIAL)

    // Glide
    implementation(Dependency.GLIDE)

    // ViewModel and LiveData
    implementation(Dependency.LIFECYCLE_EXTENSIONS)
    implementation(Dependency.LIFECYCLE_VIEW_MODEL_KTX)
    implementation(Dependency.FRAGMENT_KTX)

    // Navigation
    implementation(Dependency.NAVIGATION_FRAGMENT_KTX)
    implementation(Dependency.NAVIGATION_UI_KTX)

    // Core with Ktx
    implementation(Dependency.CORE_KTX)

    // Retrofit with Moshi
    implementation(Dependency.RETROFIT)
    implementation(Dependency.RETROFIT_MOSHI_CONVERTER)

    // Room components
    implementation(Dependency.ROOM_RUNTIME)
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")
    kapt(Dependency.ROOM_COMPILER)
    implementation(Dependency.ROOM_KTX)

    // RecyclerView
    implementation(Dependency.RECYCLERVIEW)
    implementation(Dependency.LEGACY_SUPPORT)
    implementation(Dependency.APP_COMPAT)

    // Android Tests
    androidTestImplementation(Dependency.EXT_JUNIT)
    androidTestImplementation(Dependency.ESPRESSO_CORE)

    // Unit Testing
    testImplementation(Dependency.HAMCREST)
    testImplementation(Dependency.KOTLIN_COROUTINES_TEST)

    // AndroidX Test - JVM testing
    testImplementation(Dependency.TEST_EXT_JUNIT_KTX)
    testImplementation(Dependency.TEST_CORE_KTX)
    testImplementation(Dependency.ROBOELECTRIC)
    testImplementation(Dependency.ANDROIDX_ARCH_CORE_TESTING)
    androidTestImplementation(Dependency.ANDROID_ARCH_CORE_TESTING)

    // Dependencies for Android instrumented unit tests
    androidTestImplementation(Dependency.JUNIT)
    androidTestImplementation(Dependency.KOTLIN_COROUTINES_TEST)
//
    // Dependencies for Android instrumented unit tests
    androidTestImplementation(Dependency.MOCKITO_CORE)
    androidTestImplementation(Dependency.DEXMAKER_MOCKITO)
    androidTestImplementation(Dependency.ESPRESSO_CONTRIB)

    //  Hilt and Dagger
    implementation(Dependency.HILT)
    kapt(Dependency.HILT_COMPILER)

}