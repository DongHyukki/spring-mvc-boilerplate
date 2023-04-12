package com.donghyukki.multidatasource.infra.runner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.donghyukki"])
class HyukiMultiDataSourceApplication

fun main(args: Array<String>) {
    runApplication<HyukiMultiDataSourceApplication>(*args)
}
