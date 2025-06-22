plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"


dependencies {
    implementation("org.postgresql:postgresql:42.7.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.mockito:mockito-core:5.12.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.12.0")
    testImplementation("org.assertj:assertj-core:3.25.3")
    testImplementation("org.mockito:mockito-inline:5.2.0")
}


tasks.test {
    useJUnitPlatform()
    jvmArgs = listOf(
        "-XX:+IgnoreUnrecognizedVMOptions",
        "-XX:-UseSharedSpaces",
        "-Xshare:off",
        "-Djdk.attach.allowAttachSelf=true"  // Дозволити самоприкріплення
    )

    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.example.app.ShoppingCenter"
    }

    from({
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    })
}

repositories {
    mavenCentral()
}
