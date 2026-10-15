import org.gradle.api.initialization.resolve.RepositoriesMode

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RigToon"

include(
    ":app",
    ":core:math",
    ":core:model",
    ":core:commands",
    ":core:ui",
    ":engine:rig",
    ":engine:anim",
    ":engine:render",
    ":feature:projects",
    ":feature:character",
    ":feature:rig",
    ":feature:animate",
    ":feature:export",
    ":feature:settings",
)
