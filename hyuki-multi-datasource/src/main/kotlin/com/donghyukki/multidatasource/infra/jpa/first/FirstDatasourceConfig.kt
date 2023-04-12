package com.donghyukki.multidatasource.infra.jpa.first

import org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.Properties
import javax.sql.DataSource

@DependsOn("jtaTransactionManager")
@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = "firstEntityManagerFactory",
    transactionManagerRef = "jtaTransactionManager",
    basePackages = ["com.donghyukki.multidatasource.infra.jpa.first"]
)
class FirstDatasourceConfig {

    @Primary
    @Bean(name = ["firstDataSource"])
    @ConfigurationProperties(prefix = "first.datasource")
    fun firstDataSource(): DataSource {
        return AtomikosDataSourceBean()
    }

    @Primary
    @Bean(name = ["firstEntityManagerFactory"])
    fun firstEntityManagerFactory(
        @Qualifier("firstDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        val vendorAdapter = HibernateJpaVendorAdapter().apply {
            setShowSql(true)
            setGenerateDdl(true)
            setDatabase(Database.MYSQL)
        }
        val properties = Properties().apply {
            setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
            setProperty("hibernate.transaction.jta.platform", AtomikosJtaPlatform::class.java.name)
        }

        val factory = LocalContainerEntityManagerFactoryBean().apply {
            jpaVendorAdapter = vendorAdapter
            setJtaDataSource(dataSource)
            setPackagesToScan("com.donghyukki.multidatasource.domain.entity.first")
            persistenceUnitName = "firstUnit"
            setJpaProperties(properties)
        }

        return factory
    }
}
