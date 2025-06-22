plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"


dependencies {
    implementation("org.postgresql:postgresql:42.7.3")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.12.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation ("net.bytebuddy:byte-buddy:1.14.16")
}


tasks.test {
    useJUnitPlatform()
    jvmArgs = listOf("-XX:+EnableDynamicAgentLoading",
        "-XX:+IgnoreUnrecognizedVMOptions",
        "-XX:-UseSharedSpaces",
        "-Xshare:off"    )
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
