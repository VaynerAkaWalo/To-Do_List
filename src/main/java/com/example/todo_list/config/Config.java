package com.example.todo_list.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.logging.Logger;

@Configuration
@PropertySource("classpath:errormessage.properties")
public class Config {

    @Value("${error.unauthorized}") String property;

    @PostConstruct
    void post() {
        Logger logger = Logger.getLogger(Config.class.getName());
        logger.info(property);
    }
}
