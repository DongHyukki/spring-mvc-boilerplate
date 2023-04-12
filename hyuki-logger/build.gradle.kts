dependencies {
    // web
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    // tracing
    api("org.springframework.cloud:spring-cloud-starter-sleuth")
    // logback
    api("net.logstash.logback:logstash-logback-encoder:7.2")
    implementation("com.mysql:mysql-connector-j:8.0.32")
    implementation("com.atomikos:transactions-spring-boot-starter:5.0.9")
}
