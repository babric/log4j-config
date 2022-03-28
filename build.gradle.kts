plugins {
    `maven-publish`
}

group = "babric"
version = "1.0.0"

val jar = tasks.register<Jar>("jar") {
    group = BasePlugin.BUILD_GROUP

    from(file("src/log4j2.xml"))
    archiveFileName.set("${project.name}-${project.version}.jar")
    destinationDirectory.set(layout.buildDirectory.dir("libs"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(jar)
        }
    }

    repositories {
        val url = System.getenv("MAVEN_URL")
        if (url != null) {
            maven {
                setUrl(url)
                credentials {
                    this.username = System.getenv("MAVEN_USERNAME")
                    this.password = System.getenv("MAVEN_PASSWORD")
                }
            }
        }
    }
}