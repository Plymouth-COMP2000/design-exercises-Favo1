plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.restaurant.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.restaurant.app"
        minSdk = 21
        targetSdk = 36.1.toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

// FIX: Exclude the problematic com.intellij:annotations dependency globally for this module.
// This prevents duplicate class errors when multiple libraries (or internal Android tools)
// bring in the same annotation classes, particularly older versions.
configurations.all {
    exclude(group = "com.intellij", module = "annotations")
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Room components
    implementation(libs.room.runtime.v261)
    implementation(libs.room.compiler)

    // Glide for image loading
    implementation(libs.glide.v4130)
    annotationProcessor(libs.compiler.v4130)

    // Retrofit and OkHttp
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.material.v1130)
}