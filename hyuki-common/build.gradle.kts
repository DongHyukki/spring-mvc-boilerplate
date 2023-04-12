dependencies {
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework:spring-orm")

    // etc
    implementation("io.github.openfeign:feign-jackson:12.1")
    implementation("com.auth0:java-jwt:4.2.1")
    implementation("com.mysql:mysql-connector-j:8.0.32")
    implementation("com.atomikos:transactions-spring-boot-starter:5.0.9")
}
