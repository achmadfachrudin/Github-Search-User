plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

apply(from = "../gradles/basic-kapt-config.gradle")
apply(from = "../gradles/dagger-kapt-config.gradle")

android {
    compileSdk = 32
    buildToolsVersion = "32.1.0-rc1"

    defaultConfig {
        applicationId = "com.achmad.baseandroid"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "com.achmad.baseandroid.HiltTestRunner"
        testOptions.animationsDisabled = true

        val baseUrl: String by project
        val token: String by project
        buildConfigField("String", "BASE_URL", baseUrl)
        buildConfigField("String", "TOKEN", token)
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0-beta01"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    packagingOptions {
        resources.excludes.addAll(
            listOf(
                "META-INF/AL2.0",
                "META-INF/LGPL2.1"
            )
        )
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":feature_github_data"))

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // Android
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.play:core-ktx:1.8.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    api("androidx.navigation:navigation-fragment-ktx:2.5.1")
    api("androidx.navigation:navigation-ui-ktx:2.5.1")
    api("androidx.navigation:navigation-dynamic-features-fragment:2.5.1")
    api("androidx.navigation:navigation-compose:2.5.1")

    // UI
    implementation("androidx.activity:activity-ktx:1.5.1")
    implementation("androidx.fragment:fragment-ktx:1.5.1")
    api("androidx.constraintlayout:constraintlayout:2.1.4")

    // Compose
    implementation("androidx.compose.ui:ui:1.2.0")
    implementation("androidx.compose.ui:ui-tooling:1.2.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.0")
    implementation("androidx.compose.material:material:1.2.0")
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.2.0")
    implementation("io.coil-kt:coil-compose:2.1.0")

    // Paging
    implementation("androidx.paging:paging-compose:1.0.0-alpha15")

    // DI
    implementation("com.google.dagger:hilt-android:2.43.2")
    kapt("com.google.dagger:hilt-android-compiler:2.43.2")
    implementation("javax.inject:javax.inject:1")

    // Room
    implementation("androidx.room:room-runtime:2.4.3")
    implementation("androidx.room:room-ktx:2.4.3")
    kapt("androidx.room:room-compiler:2.4.3")

    // Network
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.github.skydoves:sandwich:1.2.1")
    debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")

    // Test
    testImplementation(kotlin("test"))
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.12.5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("androidx.arch.core:core-testing:2.1.0")

    androidTestImplementation("androidx.test:core-ktx:1.4.0")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.0")
    androidTestImplementation("io.mockk:mockk-android:1.12.5")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.43.2")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.43.2")
}
