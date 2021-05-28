
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
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

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.example.translatorapp.CustomTestRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.incremental"] = "true"
            }
        }
    }

//    buildTypes {
//         getByName("release") {
//                isMinifyEnabled = false
//                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
//            }
//    }
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
    testOptions.unitTests.isIncludeAndroidResources = true

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        exclude("**/attach_hotspot_windows.dll")
        exclude("META-INF/licenses/**")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
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
    implementation(Dependency.APP_COMPAT)
    implementation(Dependency.MATERIAL)
    implementation(Dependency.KOTLIN_STDLIB)
    implementation(Dependency.CONSTRAINT_LAYOUT)
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

    // UI Testing
    debugImplementation(Dependency.FRAGMENT_TEST)
    implementation(Dependency.ANDROIDX_TEST_CORE)

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
    androidTestImplementation(Dependency.HILT_ANDROID_TESTING)
    kaptAndroidTest(Dependency.HILT_ANDROID_COMPILER)
    androidTestAnnotationProcessor(Dependency.HILT_ANDROID_COMPILER)

}