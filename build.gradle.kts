// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
    id("org.jetbrains.dokka") version "1.9.10"
}

subprojects{
    apply(plugin="org.jetbrains.dokka")

    tasks.dokkaHtml{
        outputDirectory.set(buildDir.resolve("../../documentation/html"))
    }
}