package ru.bestrestaurant.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"ru.bestrestaurant.repository", "ru.bestrestaurant.service"})
@PropertySource("classpath:db/h2.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"ru.bestrestaurant.repository"})
public class AppConfig {

//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript("classpath:db/initDB.sql")
//                .addScript("classpath:db/populateDB.sql")
//                .build();
//    }

    Environment env;

    public AppConfig(Environment env) {
        this.env = env;
    }

    @Bean
    DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.h2.Driver");
        config.setJdbcUrl(env.getProperty("database.url"));
        config.setPoolName("BestRestaurantCP");
        config.setUsername(env.getProperty("database.username"));
        config.setPassword(env.getProperty("database.password"));
        return new HikariDataSource(config);
    }

    /*
        https://www.baeldung.com/the-persistence-layer-with-spring-and-jpa
        Starting with Spring 3.1, the persistence.xml is no longer necessary.
        LocalContainerEntityManagerFactoryBean now supports a packagesToScan property
        where the packages to scan for @Entity classes can be specified.
     */
    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(Boolean.getBoolean(env.getProperty("showSQL")));
        LocalContainerEntityManagerFactoryBean localCEMFB = new LocalContainerEntityManagerFactoryBean();
        localCEMFB.setDataSource(dataSource());
        localCEMFB.setPackagesToScan("ru.bestrestaurant.model");
        localCEMFB.setJpaVendorAdapter(vendorAdapter);
        localCEMFB.setJpaProperties(additionalProperties());
        return localCEMFB;
    }

    @Bean
    JpaTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty(AvailableSettings.FORMAT_SQL, env.getProperty("hibernate.format_sql"));
        properties.setProperty(AvailableSettings.USE_SQL_COMMENTS, env.getProperty("hibernate.use_sql_comments"));
        properties.setProperty(AvailableSettings.JPA_PROXY_COMPLIANCE, "false");
        return properties;
    }
}
