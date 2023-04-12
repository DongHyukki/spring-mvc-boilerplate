dependencies {
    implementation(project(":hyuki-common"))
    implementation(project(":hyuki-logger"))

    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("com.mysql:mysql-connector-j:8.0.32")
    implementation("com.atomikos:transactions-spring-boot-starter:5.0.9")
}
tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
