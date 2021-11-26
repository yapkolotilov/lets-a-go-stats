import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    id("maven-publish")
}

group = "me.yapko"
version = "1.0"

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation("joda-time", "joda-time", "2.10.9")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            /** Configure path of your package repository on Github
             *  Replace GITHUB_USERID with your/organisation Github userID and REPOSITORY with the repository name on GitHub
             */
            url = uri("https://maven.pkg.github.com/yapkolotilov/lets-a-go-stats") // Github Package
            credentials {
                //Fetch these details from the properties file or from Environment variables
                username = "yapkolotilov"
                password = "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"
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "me.yapko"
            artifactId = "lets-a-go-stats"
            version = "1.0"

            from(components["java"])
        }
    }
}