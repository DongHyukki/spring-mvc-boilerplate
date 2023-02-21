dependencies {
    implementation(project(":hyuki-common"))
    implementation(project(":hyuki-logger"))

    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    testImplementation("com.h2database:h2:2.1.214")
    testImplementation("it.ozimov:embedded-redis:0.7.1")
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
