import org.jetbrains.kotlin.gradle.plugin.statistics.ReportStatisticsToElasticSearch.password
import org.jetbrains.kotlin.gradle.plugin.statistics.ReportStatisticsToElasticSearch.url
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
    id("org.jlleitschuh.gradle.ktlint-idea") version "10.1.0"

    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"

    idea
}

group = "net.chandol"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

sourceSets {
    create("integrationTest") {
        compileClasspath += sourceSets.main.get().output + sourceSets.test.get().output
        runtimeClasspath += sourceSets.main.get().output + sourceSets.test.get().output

        resources.srcDir(file("src/integrationTest/resources"))
    }
}

val integrationTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.implementation.get(), configurations.testImplementation.get())
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    runtimeOnly("io.r2dbc:r2dbc-h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest:kotest-runner-junit5:5.1.0")
    testImplementation("io.kotest:kotest-assertions-core:5.1.0")
    testImplementation("io.kotest:kotest-assertions-json:5.1.0")

    integrationTestImplementation("io.r2dbc:r2dbc-h2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

project.tasks.named("processIntegrationTestResources", Copy::class.java) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"

    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath

    shouldRunAfter("test")
}

idea.module {
    testSourceDirs = testSourceDirs + project.sourceSets.getByName("integrationTest").allSource.srcDirs
    testResourceDirs = testResourceDirs + project.sourceSets.getByName("integrationTest").resources.srcDirs
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
    val containerRegistry = System.getenv("CONTAINER_REGISTRY") ?: "ghcr.io"
    val containerImageName = System.getenv("CONTAINER_IMAGE_NAME") ?: "digitalservice4germany/${rootProject.name}"
    val containerImageVersion = System.getenv("CONTAINER_IMAGE_VERSION") ?: "latest"

    imageName = "$containerRegistry/$containerImageName:$containerImageVersion"
    isPublish = false
    docker {
        publishRegistry {
            username = System.getenv("CONTAINER_REGISTRY_USER") ?: ""
            password = System.getenv("CONTAINER_REGISTRY_PASSWORD") ?: ""
            url = "https://$containerRegistry"
        }
    }
}
