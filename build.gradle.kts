plugins {
    kotlin("jvm") version "2.0.20"
    application
}

version = "1.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/exposed/maven") // for Exposed
    maven("https://jitpack.io") // for kotlin-telegram-bot
}


kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.0.7")
    implementation("org.jetbrains.exposed:exposed-core:0.47.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.47.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.47.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.47.0")
    implementation("org.xerial:sqlite-jdbc:3.45.3.0")
    implementation("ch.qos.logback:logback-classic:1.4.11")
}

application {
    mainClass.set("MainKt") // or "bot.MainKt" if you use a package
}

sourceSets {
    main {
        java.srcDirs("src/main/kotlin")
    }
}
