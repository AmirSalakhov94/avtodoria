package ru.avtodoria.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(value = {"ru.avtodoria"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {DataSourceConfig.class})
})
public class TestConfiguration {

    public TestConfiguration() {
        System.setProperty("auth", "off");
    }

}