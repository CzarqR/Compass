@file:Suppress("LocalVariableName")

buildscript {
    val compose_version by extra("1.0.0-beta09")
    val hilt_version by extra("2.37")
    val timber_version by extra("4.7.1")
    val compose_lifecycle_version by extra("1.0.0-alpha07")
    
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.0-alpha02")
        /**
         * must be 1.5.10
         *
         * [hilt 2.37] + [jetpack compose beta-09] + [kotlin 1.5.20] produces error
         */
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.37")
    }

    
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

