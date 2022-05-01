import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "me.saulpalv"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("MainKt")
}
dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.eclipse.jetty:jetty-servlet:11.0.8")
    implementation("org.eclipse.jetty:jetty-server:11.0.8")
    implementation("org.eclipse.jetty.websocket:websocket-jetty-client:11.0.8")
    implementation("org.eclipse.jetty.websocket:websocket-jetty-server:11.0.8")
    implementation("org.eclipse.jetty:jetty-annotations:11.0.8")
    implementation("org.slf4j:slf4j-reload4j:2.0.0-alpha6")
    implementation("org.slf4j:slf4j-api:2.0.0-alpha6")
    implementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("commons-io:commons-io:2.11.0")
}