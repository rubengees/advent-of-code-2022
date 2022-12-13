import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.joinToCode
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jmailen.gradle.kotlinter.tasks.LintTask
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.streams.asSequence

plugins {
    kotlin("multiplatform") version "1.7.21"
    id("org.jmailen.kotlinter") version "3.12.0"
}

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("com.squareup:kotlinpoet:1.12.0")
    }
}

group = "de.rubengees"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")

    when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    targets.withType<KotlinNativeTarget> {
        binaries {
            executable()
        }
    }

    sourceSets {
        val nativeMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            }

            kotlin {
                srcDir(buildDir.toPath().resolve("generated"))
            }
        }

        val nativeTest by getting
    }
}

open class InlineResourcesTask : DefaultTask() {

    @get:InputDirectory
    val resourcesDir: Path = project.file("src/nativeMain/resources").toPath()

    @get:OutputFile
    val outFile: Path = project.buildDir.toPath().resolve("generated/InlineResources.kt")

    @TaskAction
    fun run() {
        val resources = Files.list(resourcesDir).asSequence()
            .map { resourcesDir.relativize(it) to Files.readString(it).trim() }
            .map { (key, value) -> CodeBlock.of("%S to %S", key, value) }
            .toList()

        val fileSpec = FileSpec
            .builder("", "InlineResources")
            .addProperty(
                PropertySpec
                    .builder(
                        "inlineResources",
                        Map::class.parameterizedBy(String::class, String::class),
                    )
                    .initializer(CodeBlock.of("mapOf(\n%L\n)", resources.joinToCode(",\n")))
                    .build()
            )
            .build()

        Files.writeString(outFile, fileSpec.toString())
    }
}

open class GenerateDayMappingTask : DefaultTask() {

    private companion object {
        private val dayRegex = Regex("""Day(\d+)""")
    }

    @get:InputDirectory
    val srcDir: Path = project.file("src/nativeMain/kotlin").toPath()

    @get:OutputFile
    val outFile: Path = project.buildDir.toPath().resolve("generated/DayMapping.kt")

    @TaskAction
    fun run() {
        val classes = Files.list(srcDir).asSequence()
            .map { it.fileName.toString().substringBeforeLast(".") }
            .mapNotNull { dayRegex.matchEntire(it)?.groupValues?.get(1)?.toIntOrNull() }
            .sorted()
            .map { day -> CodeBlock.of("%L to %L", day, "Day$day()") }
            .toList()

        val fileSpec = FileSpec
            .builder("", "DayMapping")
            .addProperty(
                PropertySpec
                    .builder(
                        "dayMapping",
                        Map::class.asTypeName().parameterizedBy(
                            Int::class.asTypeName(),
                            ClassName("", "Day")
                        )
                    )
                    .initializer(CodeBlock.of("linkedMapOf(\n%L\n)", classes.joinToCode(",\n")))
                    .build()
            )
            .build()

        Files.writeString(outFile, fileSpec.toString())
    }
}

open class NewDayTask : DefaultTask() {

    @set:Option(option = "day", description = "The day to generate")
    @get:Input
    var day: String = ""

    @get:OutputDirectory
    val srcDir: Path = project.file("src/nativeMain/kotlin").toPath()

    @get:OutputDirectory
    val testDir: Path = project.file("src/nativeTest/kotlin").toPath()

    @get:OutputDirectory
    val resourcesDir: Path = project.file("src/nativeMain/resources").toPath()

    @TaskAction
    fun run() {
        require(day.toIntOrNull() != null) { "Missing day parameter" }
        require(day.toInt() >= 1) { "Day parameter must be at least 1" }

        val dayTemplate = Files.readString(Paths.get("day.kt.template"))
        val dayTestTemplate = Files.readString(Paths.get("day-test.kt.template"))

        Files.write(srcDir.resolve("Day$day.kt"), dayTemplate.replace("{day}", day).toByteArray())
        Files.write(testDir.resolve("Day${day}Test.kt"), dayTestTemplate.replace("{day}", day).toByteArray())
        Files.write(resourcesDir.resolve("day$day.txt"), "".toByteArray())
    }
}

tasks.register<InlineResourcesTask>("inlineResources") {
    description = "Inlines resources into a generated file."
    group = "build"
}

tasks.register<GenerateDayMappingTask>("generateDayMapping") {
    description = "Generates day mapping file."
    group = "build"
}

tasks.register<NewDayTask>("newDay") {
    description = "Generates a new day."
    group = "generate"
}

tasks.findByName("compileKotlinNative")?.dependsOn("inlineResources", "generateDayMapping")

tasks.withType(LintTask::class.java) {
    exclude("InlineResources.kt", "DayMapping.kt")
}

tasks.withType(ProcessResources::class.java) {
    enabled = false
}
