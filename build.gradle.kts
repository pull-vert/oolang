import org.gradle.tooling.GradleConnector

plugins {
    `maven-publish`
    signing
    alias(libs.plugins.release)
}

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    // --------------- publishing ---------------

    publishing {
        repositories {
            maven {
                url = uri(layout.buildDirectory.dir("repos/releases"))
            }
        }

        publications.withType<MavenPublication> {
                pom {
                    name = project.name
                    description = "The Oolang JVM language"
                    url = "https://github.com/pull-vert/oolang"

                    licenses {
                        license {
                            name = "The Unlicence"
                            url = "https://unlicense.org"
                        }
                    }

                    developers {
                        developer {
                            name.set("pull-vert")
                            url.set("https://github.com/pull-vert")
                        }
                    }

                    scm {
                        connection = "scm:git:git://github.com/pull-vert/oolang"
                        developerConnection = "scm:git:git://github.com/pull-vert/oolang.git"
                        url = "https://github.com/pull-vert/oolang.git"
                    }
                }
            }
        }

    signing {
        // Require signing.keyId, signing.password and signing.secretKeyRingFile
        sign(publishing.publications)
    }
    }

// workaround : https://github.com/researchgate/gradle-release/issues/304#issuecomment-1083692649
configure(listOf(tasks.release, tasks.runBuildTasks)) {
    configure {
        actions.clear()
        doLast {
            GradleConnector
                .newConnector()
                .forProjectDirectory(layout.projectDirectory.asFile)
                .connect()
                .use { projectConnection ->
                    val buildLauncher = projectConnection
                        .newBuild()
                        .forTasks(*tasks.toTypedArray())
                        .setStandardInput(System.`in`)
                        .setStandardOutput(System.out)
                        .setStandardError(System.err)
                    gradle.startParameter.excludedTaskNames.forEach {
                        buildLauncher.addArguments("-x", it)
                    }
                    buildLauncher.run()
                }
        }
    }
}

// when the Gradle version changes:
// -> execute ./gradlew wrapper, then remove .gradle directory, then execute ./gradlew wrapper again
tasks.wrapper {
    gradleVersion = "9.4.1"
    distributionType = Wrapper.DistributionType.ALL
}
