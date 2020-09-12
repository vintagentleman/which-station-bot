plugins {
    application
    kotlin("jvm") version "1.3.61"
    id("com.github.johnrengelman.shadow") version "5.0.0"
    id("org.jmailen.kotlinter") version "3.0.2"
}

group = "com.justai.jaicf"
version = "1.0.0"

val jaicf = "0.6.1"
val slf4j = "1.7.30"
val ktor = "1.3.1"
val gson = "2.8.6"
val fuel = "2.2.3"
val klaxon = "5.4"

application {
    mainClassName = "com.justai.jaicf.template.connections.PollingConnectionKt"
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven(url = "https://jitpack.io") {
        name = "jitpack"
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.slf4j:slf4j-simple:$slf4j")
    implementation("org.slf4j:slf4j-log4j12:$slf4j")

    implementation("com.justai.jaicf:core:$jaicf")
    implementation("com.justai.jaicf:mongo:$jaicf")
    implementation("com.justai.jaicf:jaicp:$jaicf")
    implementation("com.justai.jaicf:caila:$jaicf")
    implementation("com.justai.jaicf:telegram:$jaicf")

    implementation("io.ktor:ktor-server-netty:$ktor")

    implementation("com.google.code.gson:gson:$gson")
    implementation("com.github.kittinunf.fuel:fuel:$fuel")
    implementation("com.github.kittinunf.fuel:fuel-json:$fuel")

    implementation("com.beust:klaxon:$klaxon")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf(
                "Main-Class" to application.mainClassName
            )
        )
    }
}

tasks.create("stage") {
    dependsOn("shadowJar")
}

apply(plugin = "org.jmailen.kotlinter")
