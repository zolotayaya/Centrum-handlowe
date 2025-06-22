plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"


dependencies {
    implementation("org.postgresql:postgresql:42.7.3")
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
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
