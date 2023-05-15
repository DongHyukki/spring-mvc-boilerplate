dependencies {
    implementation(project(":hyuki-common"))
    implementation(project(":hyuki-logger"))

    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("com.mysql:mysql-connector-j:8.0.32")
    implementation("com.atomikos:transactions-spring-boot-starter:5.0.9")

    implementation("com.h2database:h2:2.1.214")
    implementation("it.ozimov:embedded-redis:0.7.3") {
        exclude(group = "org.slf4j", module = "slf4j-api")
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }
    implementation("mysql:mysql-connector-java:8.0.32")

}
tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
