dependencies {
    // web
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    // tracing
    api("org.springframework.cloud:spring-cloud-starter-sleuth")
    // logback
    api("net.logstash.logback:logstash-logback-encoder:7.2")
}
