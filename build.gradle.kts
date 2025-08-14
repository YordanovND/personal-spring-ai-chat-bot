plugins {
    id("java")
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // BOM — manages versions for all Spring AI artifacts
    implementation(platform("org.springframework.ai:spring-ai-bom:1.0.1"))

    // Ollama starter
    implementation("org.springframework.ai:spring-ai-starter-model-ollama")

    // Normal Spring Boot stuff
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    mainClass.set("org.example.Application")
}
