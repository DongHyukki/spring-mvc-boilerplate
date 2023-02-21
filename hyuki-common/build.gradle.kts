dependencies {
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework:spring-orm")

    // etc
    implementation("io.github.openfeign:feign-jackson:12.1")
    implementation("com.auth0:java-jwt:4.2.1")
}
