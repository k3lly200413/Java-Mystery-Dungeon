plugins {
    // Automatically downloads the correct java version to run the static analyzers
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("org.danilopianini.gradle-java-qa") version "1.75.0"
}

rootProject.name = "sample-gradle-project"
