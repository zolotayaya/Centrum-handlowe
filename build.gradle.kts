plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

//repositories {
//    mavenCentral()
//}

dependencies {
    implementation("org.postgresql:postgresql:42.7.3")
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
//subprojects {
//    apply(plugin = "java")
////
////    java {
////        toolchain {
////            languageVersion.set(JavaLanguageVersion.of(17))
////        }
//    }
repositories {
    mavenCentral()
}
