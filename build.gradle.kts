// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    dependencies {
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.5")
    }
    repositories {
        mavenCentral()
    }
}
plugins {
    id("com.android.application") version "8.1.0-beta01" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}