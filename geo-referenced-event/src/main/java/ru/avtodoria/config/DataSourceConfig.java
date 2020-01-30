package ru.avtodoria.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.Properties;

//@Configuration
@PropertySource("classpath:application.yml")
public class DataSourceConfig {

    @Value("${hikari.ds.dataSourceClassName}")
    private String dataSourceClassName;

    @Value("${hikari.ds.databaseName}")
    private String databaseName;

    @Value("${hikari.ds.serverName}")
    private String serverName;

    @Value("${hikari.ds.user}")
    private String user;

    @Value("${hikari.ds.password}")
    private String password;

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSourceClassName(dataSourceClassName);

        Properties p = new Properties();
        p.setProperty("databaseName", databaseName);
        p.setProperty("serverName", serverName);
        p.setProperty("user", user);
        p.setProperty("password", password);
        hikariConfig.setDataSourceProperties(p);

        return hikariConfig;
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }
}
