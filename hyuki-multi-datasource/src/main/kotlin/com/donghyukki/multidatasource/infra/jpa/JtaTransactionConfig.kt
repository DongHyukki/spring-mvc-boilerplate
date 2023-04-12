package com.donghyukki.multidatasource.infra.jpa

import com.atomikos.icatch.jta.UserTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.jta.JtaTransactionManager

@Configuration
@EnableTransactionManagement
class JtaTransactionConfig {

    @Bean(name = ["jtaTransactionManager"])
    fun jtaTransactionManager(): JtaTransactionManager {
        return JtaTransactionManager().apply {
            transactionManager = userTransactionManager()
            userTransaction = userTransactionManager()
        }
    }

    @Bean
    fun userTransactionManager(): UserTransactionManager {
        return UserTransactionManager().apply {
            setTransactionTimeout(10000)
            forceShutdown = true
        }
    }
}
