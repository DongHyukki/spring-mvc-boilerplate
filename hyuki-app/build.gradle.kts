dependencies {
    implementation(project(":hyuki-common"))
    implementation(project(":hyuki-logger"))

    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    testImplementation("com.h2database:h2:2.1.214")
    testImplementation("it.ozimov:embedded-redis:0.7.1")
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
