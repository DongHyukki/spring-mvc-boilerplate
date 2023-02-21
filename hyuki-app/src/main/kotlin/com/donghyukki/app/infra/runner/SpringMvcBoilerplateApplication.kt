package com.donghyukki.app.infra.runner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.donghyukki"])
class SpringMvcBoilerplateApplication

fun main(args: Array<String>) {
    runApplication<SpringMvcBoilerplateApplication>(*args)
}
