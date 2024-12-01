plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.squareup.sqldelight")
}

android {
    namespace = "com.example.userlookup"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.userlookup"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {


    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("androidx.compose.runtime:runtime:1.6.8")
    implementation("com.squareup.sqldelight:coroutines-extensions:1.4.3")
    implementation("com.squareup.sqldelight:runtime:1.4.3")
    implementation("com.squareup.sqldelight:android-driver:1.4.3")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("io.ktor:ktor-client-core:2.2.1")
    implementation("io.ktor:ktor-client-serialization:2.2.1")
    implementation("io.ktor:ktor-client-logging:2.2.1")
    implementation("io.ktor:ktor-client-android:2.2.1")
    implementation("io.ktor:ktor-client-okhttp:2.2.1")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

}

sqldelight{
    database("UserDB"){
        packageName = "com.example.userlookup"
        linkSqlite = false
        version = 1
    }
}