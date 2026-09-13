import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)

    // dagger hilt
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.gms.google.services)

}

android {
    namespace = "com.besha.shinobihub"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.besha.shinobihub"
        minSdk = 24
        targetSdk = 36
        versionCode = 9
        versionName = "1.0.8"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        android.buildFeatures.buildConfig = true
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(localPropertiesFile.inputStream())
        }

        val apiKey = localProperties.getProperty("API_KEY") ?: throw GradleException("API key not found in local.properties")

        buildConfigField("String", "API_KEY", "\"$apiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.8.0"
    }
}

dependencies {

    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.coil)

    implementation(libs.core)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.androidx.foundation) // or latest

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.kotlinx.serialization.json)

    // Dagger Hilt
    implementation(libs.hilt.android)
    implementation(libs.firebase.messaging)
    ksp(libs.hilt.compiler)

    implementation(libs.hilt.navigation.compose)

    // retrofit
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // coil
    implementation(libs.coil.compose)

    implementation(libs.glide)

    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk)

    implementation(libs.androidx.material.icons.extended)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.turbine)
    testImplementation(libs.truth)
}
