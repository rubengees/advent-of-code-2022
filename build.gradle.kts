import java.nio.file.Files
import java.nio.file.Paths

plugins {
    kotlin("multiplatform") version "1.7.21"
}

group = "de.rubengees"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        val nativeMain by getting {
            dependencies {
                implementation("com.squareup.okio:okio:3.2.0")
            }
        }

        val nativeTest by getting
    }
}

open class NewDayTask : DefaultTask() {

    @set:Option(option = "day", description = "The day to generate")
    @get:Input
    var day: String = ""

    @TaskAction
    fun run() {
        require(day.toIntOrNull() != null) { "Missing day parameter" }
        require(day.toInt() >= 1) { "Day parameter must be at least 1" }

        val dayTemplate = Files.readString(Paths.get("day.kt.template"))
        val dayTestTemplate = Files.readString(Paths.get("day-test.kt.template"))

        Files.write(Paths.get("src/nativeMain/kotlin/Day$day.kt"), dayTemplate.replace("{day}", day).toByteArray())

        Files.write(
            Paths.get("src/nativeTest/kotlin/Day${day}Test.kt"),
            dayTestTemplate.replace("{day}", day).toByteArray()
        )

        Files.write(Paths.get("src/nativeTest/resources/day$day.txt"), "".toByteArray())
    }
}

tasks.register<NewDayTask>("newDay") {
    description = "Generates a new day."
    group = "help"
}
