import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.Directory
import org.gradle.api.file.FileTree
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskProvider
import org.gradle.language.base.plugins.LifecycleBasePlugin
import java.io.File

plugins {
    base

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
}

group = "com.rigtoon"
version = "0.1.0"

val detektCli: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
    description = "Isolated runtime for detekt command-line analysis."
}

val ktlintCli: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
    description = "Isolated runtime for ktlint command-line analysis."
}

dependencies {
    add(detektCli.name, libs.detekt.cli)
    add(ktlintCli.name, libs.ktlint.cli)
}

val kotlinSources: FileTree = fileTree(rootDir) {
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/build/**")
    exclude("**/.gradle/**")
    exclude("**/.kotlin/**")
    exclude("**/generated/**")
}

val detektConfiguration: File =
    layout.projectDirectory.file("config/detekt/detekt.yml").asFile

val detektReportDirectory: Provider<Directory> =
    layout.buildDirectory.dir("reports/detekt")

val detektTask: TaskProvider<JavaExec> = tasks.register<JavaExec>("detekt") {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Runs detekt across Kotlin source and build scripts."
    classpath = detektCli
    mainClass.set("io.gitlab.arturbosch.detekt.cli.Main")
    maxHeapSize = "768m"

    inputs.files(kotlinSources).withPathSensitivity(PathSensitivity.RELATIVE)
    inputs.file(detektConfiguration).withPathSensitivity(PathSensitivity.RELATIVE)
    outputs.dir(detektReportDirectory)

    args(
        "--input",
        rootDir.absolutePath,
        "--base-path",
        rootDir.absolutePath,
        "--config",
        detektConfiguration.absolutePath,
        "--build-upon-default-config",
        "--excludes",
        "**/build/**,**/.gradle/**,**/.kotlin/**,**/generated/**",
        "--report",
        "xml:${detektReportDirectory.get().file("detekt.xml").asFile.absolutePath}",
        "--report",
        "html:${detektReportDirectory.get().file("detekt.html").asFile.absolutePath}",
    )

    doFirst {
        detektReportDirectory.get().asFile.mkdirs()
    }
}

val ktlintCheckTask: TaskProvider<JavaExec> = tasks.register<JavaExec>("ktlintCheck") {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Checks Kotlin formatting without modifying files."
    classpath = ktlintCli
    mainClass.set("com.pinterest.ktlint.Main")
    maxHeapSize = "512m"
    workingDir = rootDir

    inputs.files(kotlinSources).withPathSensitivity(PathSensitivity.RELATIVE)
    inputs.file(layout.projectDirectory.file(".editorconfig"))
        .withPathSensitivity(PathSensitivity.RELATIVE)

    args(
        "--relative",
        "**/*.kt",
        "**/*.kts",
        "!**/build/**",
        "!**/.gradle/**",
        "!**/.kotlin/**",
        "!**/generated/**",
    )
}

tasks.register<JavaExec>("ktlintFormat") {
    group = "formatting"
    description = "Formats Kotlin source and build scripts in place."
    classpath = ktlintCli
    mainClass.set("com.pinterest.ktlint.Main")
    maxHeapSize = "512m"
    workingDir = rootDir

    args(
        "--format",
        "--relative",
        "**/*.kt",
        "**/*.kts",
        "!**/build/**",
        "!**/.gradle/**",
        "!**/.kotlin/**",
        "!**/generated/**",
    )
}

tasks.named("check") {
    dependsOn(detektTask, ktlintCheckTask)
    dependsOn(subprojects.map { "${it.path}:check" })
}
