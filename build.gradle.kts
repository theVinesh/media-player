// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlin_version by extra("1.3.72")
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0")
        classpath(kotlin("gradle-plugin", version = "1.3.72"))

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
