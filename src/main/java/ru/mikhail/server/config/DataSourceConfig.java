package ru.mikhail.server.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
public class DataSourceConfig {
    private transient final Logger logger = Logger.getLogger(this.getClass());
    @Value("${db.url}")
    String url;
    @Value("${db.driver}")
    String driver;
    @Value("${db.username}")
    String username;
    @Value("${db.password}")
    String password;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dm = new DriverManagerDataSource();
        dm.setUrl(url);
        dm.setDriverClassName(driver);
        dm.setUsername(username);
        dm.setPassword(password);
        logger.info("Database instantiated.");
        return dm;
    }
}
