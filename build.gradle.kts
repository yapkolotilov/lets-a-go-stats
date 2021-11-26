import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    id("maven-publish")
}

val groupName = "me.yapko"
val artifactName = "lets_a_go_stats"
val versionName = "1.1"

group = groupName
version = versionName

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
            url = uri("https://maven.pkg.github.com/yapkolotilov/lets-a-go-stats")
            credentials {
                username = System.getenv("GITHUB_USERNAME").orEmpty()
                password = System.getenv("GITHUB_TOKEN").orEmpty()
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = groupName
            artifactId = artifactName
            version = versionName

            from(components["java"])
        }
    }
}