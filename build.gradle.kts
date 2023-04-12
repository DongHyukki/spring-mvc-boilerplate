import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // kotlin
    kotlin("jvm") version "1.6.21"

    // lint
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"

    // spring
    kotlin("plugin.spring") version "1.6.21" apply false
    id("org.springframework.boot") version "2.7.5" apply false
    id("io.spring.dependency-management") version "1.0.15.RELEASE" apply false
}

allprojects {
    group = "co.donghyukki"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

java.sourceCompatibility = JavaVersion.VERSION_17
extra["springCloudVersion"] = "2021.0.4"

val kotestVersion = "5.5.3"
val kotestSpringExtensionVersion = "1.1.2"
val openapiVersion = "1.6.14"
val mockkVersion = "1.9.3"
val springmockkVersion = "3.1.2"
dependencies {
    implementation("com.mysql:mysql-connector-j:8.0.32")
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension> {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        // jackson
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        // validation
        implementation("org.springframework.boot:spring-boot-starter-validation")
        // openapi
        implementation("org.springdoc:springdoc-openapi-data-rest:$openapiVersion")
        implementation("org.springdoc:springdoc-openapi-ui:$openapiVersion")
        implementation("org.springdoc:springdoc-openapi-kotlin:$openapiVersion")
        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:$kotestSpringExtensionVersion")
        testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
        testImplementation("io.mockk:mockk:$mockkVersion")
        testImplementation("com.ninja-squad:springmockk:$springmockkVersion")
    }

    tasks.getByName("bootJar") {
        enabled = false
    }

    tasks.getByName("jar") {
        enabled = true
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
    // gradlew build --Pci=true
    // gradle command 시 해당 Flag 값 (CI) 을 환경 변수로 저장
    tasks.withType<Test> {
        environment["ci"] = System.getProperty("ci")
        useJUnitPlatform()
    }
}
