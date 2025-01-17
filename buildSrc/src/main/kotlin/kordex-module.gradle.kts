import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")

    id("io.gitlab.arturbosch.detekt")
    id("org.cadixdev.licenser")
}

abstract class KordexExtension {
    abstract val jvmTarget: Property<String>
    abstract val javaVersion: Property<JavaVersion>
}

val kordexExtensionName = "kordex"

extensions.create<KordexExtension>(kordexExtensionName)

val sourceJar = task("sourceJar", Jar::class) {
    dependsOn(tasks["classes"])
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar = task("javadocJar", Jar::class) {
    dependsOn("dokkaJavadoc")
    archiveClassifier.set("javadoc")
    from(tasks.javadoc)
    from(tasks.javadoc)
}

tasks {
    build {
        finalizedBy(sourceJar, javadocJar)
    }

    kotlin {
        explicitApi()
    }

    jar {
        from(rootProject.file("build/LICENSE-kordex"))
    }

    afterEvaluate {
        val extension = project.extensions.getByName<KordexExtension>(kordexExtensionName)

        rootProject.file("LICENSE").copyTo(rootProject.file("build/LICENSE-kordex"), true)

        java {
            sourceCompatibility = extension.javaVersion.getOrElse(JavaVersion.VERSION_1_8)
        }

        withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = extension.jvmTarget.getOrElse("1.8")
            }
        }
    }
}

detekt {
    buildUponDefaultConfig = true
    config = files("$rootDir/detekt.yml")

    autoCorrect = true
}

license {
    setHeader(rootProject.file("LICENSE"))
    ignoreFailures(System.getenv()["CI"] == null)
}
