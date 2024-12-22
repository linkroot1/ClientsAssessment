package ru.pavlenko.clientsassessment.configuration;

import org.camunda.bpm.spring.boot.starter.configuration.impl.AbstractCamundaConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class CamundaConfiguration extends AbstractCamundaConfiguration {
    @Value("${camunda.bpm.db.username}")
    private String username;

    @Value("${camunda.bpm.db.password}")
    private String password;

    @Value("${camunda.bpm.db.schema-name}")
    private String url;

    @Value("${camunda.bpm.db.driver-class-name}")
    private String driver;

    @Bean(name = "camundaBpmDataSource")
    @ConfigurationProperties("camunda.bpm.db")
    public DataSource camundaDataSource() {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driver)
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(camundaDataSource());
    }
}
