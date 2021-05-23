import org.gradle.internal.impldep.org.fusesource.jansi.AnsiRenderer.test

//import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.android.extensions")
}

android {
    compileSdkVersion(Versions.COMPILE_SDK)
    buildToolsVersion(Versions.BUILD_TOOLS)

    defaultConfig {
        minSdkVersion(Versions.MIN_SDK)
        targetSdkVersion(Versions.TARGET_SDK)
        versionCode = Versions.APP_VERSION
        versionName = Versions.APP_VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

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

    testOptions.unitTests.isIncludeAndroidResources = true

}

dependencies {

    implementation(Dependency.KOTLIN_STDLIB)
    implementation(Dependency.CORE_KTX)

    // Moshi
    implementation(Dependency.MOSHI)
    implementation(Dependency.MOSHI_KOTLIN)

    // Retrofit with Moshi Converter
    implementation(Dependency.RETROFIT)
    implementation(Dependency.RETROFIT_MOSHI_CONVERTER)

    // OkHttp
    implementation(Dependency.OKHTTP)

    // Room components
    implementation(Dependency.ROOM_RUNTIME)
    kapt(Dependency.ROOM_COMPILER)
    implementation(Dependency.ROOM_KTX)

    // optional - Test helpers
    testImplementation(Dependency.ROOM_TESTING)

    //  Hilt and Dagger
    implementation(Dependency.HILT)
    kapt(Dependency.HILT_COMPILER)
    testImplementation(Dependency.HILT_ANDROID_TESTING)
//    kaptTest(Dependency.HILT_ANDROID_COMPILER)
    testAnnotationProcessor(Dependency.HILT_ANDROID_COMPILER)
    androidTestImplementation(Dependency.HILT_ANDROID_TESTING)
//    kaptAndroidTest(Dependency.HILT_ANDROID_COMPILER)
    androidTestAnnotationProcessor(Dependency.HILT_ANDROID_COMPILER)

    // Android Tests
    androidTestImplementation(Dependency.EXT_JUNIT)
    androidTestImplementation(Dependency.ESPRESSO_CORE)

    // Unit Testing
    testImplementation(Dependency.HAMCREST)
    testImplementation(Dependency.KOTLIN_COROUTINES_TEST)
    testImplementation(Dependency.MOCKITO_CORE)

    // AndroidX Test - JVM testing
    testImplementation(Dependency.TEST_EXT_JUNIT_KTX)
    testImplementation(Dependency.TEST_CORE_KTX)
    testImplementation(Dependency.ROBOELECTRIC)
    testImplementation(Dependency.ANDROIDX_ARCH_CORE_TESTING)
    androidTestImplementation(Dependency.ANDROID_ARCH_CORE_TESTING)

    // Dependencies for Android instrumented unit tests
    androidTestImplementation(Dependency.JUNIT)
    androidTestImplementation(Dependency.KOTLIN_COROUTINES_TEST)


    // Dependencies for Android instrumented unit tests
    androidTestImplementation(Dependency.MOCKITO_CORE)
    androidTestImplementation(Dependency.DEXMAKER_MOCKITO)
    androidTestImplementation(Dependency.ESPRESSO_CONTRIB)
}

