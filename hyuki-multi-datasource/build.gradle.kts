plugins {
    kotlin("plugin.jpa") version "1.6.21"
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

dependencies {
    implementation(project(":hyuki-common"))
    implementation(project(":hyuki-logger"))

    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.atomikos:transactions-spring-boot-starter:5.0.9")

    runtimeOnly("mysql:mysql-connector-java")
    testImplementation("com.h2database:h2:2.1.214")
}

// buildscript {
//     dependencies {
//         classpath("org.flywaydb:flyway-mysql:8.5.13")
//     }
// }

// flyway {
//     url = "jdbc:mysql://localhost:3308?useSSL=false&allowPublicKeyRetrieval=true"
//     user = "root"
//     password = "root"
//     locations = arrayOf("filesystem:src/main/resources/db/migration/")
//     schemas = arrayOf("thinking_table")
//     createSchemas = true
//     encoding = "utf8"
//     baselineOnMigrate = true
//     cleanDisabled = false
//     failOnMissingLocations = true
// }

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
