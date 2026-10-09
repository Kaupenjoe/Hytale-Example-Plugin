plugins {
    idea
    `maven-publish`
    alias(libs.plugins.hytale.tools)
}

group = "com.example"
version = "0.1.0"
val javaVersion = 25

// Repositories (Maven Central, the Hytale server repos, AzureDoom/HytaleModding mavens,
// CurseMaven, Modtale, Modifold) are added by the Hytale Gradle Plugin automatically.

dependencies {
    compileOnly(libs.jetbrains.annotations)
    compileOnly(libs.jspecify)
}

// Most values are read from gradle.properties (see the "Hytale" section there).
// Anything set here overrides the matching property.
hytaleTools {
    // Develop against the pre-release patchline instead of release.
    // patchline = "pre-release"

    // Pin the manifest's ServerVersion instead of using ">=<resolved server version>".
    // manifestServerVersion = "*"

    // Extra launch settings for ./gradlew runServer
    // serverJvmArgs("-Xms2G", "-Xmx2G")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(javaVersion)
    }

    withSourcesJar()
}

tasks.withType<Jar>().configureEach {
    manifest {
        attributes["Specification-Title"] = rootProject.name
        attributes["Specification-Version"] = version
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] =
            providers.environmentVariable("COMMIT_SHA_SHORT")
                .map { "${version}-${it}" }
                .getOrElse(version.toString())
    }
}

publishing {
    repositories {
        // This is where you put repositories that you want to publish to.
        // Do NOT put repositories for your dependencies here.
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

// IDEA no longer automatically downloads sources/javadoc jars for dependencies, so we need to explicitly enable the behavior.
// The Hytale Gradle Plugin also attaches decompiled server sources and hosted Javadocs when the idea plugin is applied.
idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}
