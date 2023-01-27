package com.cdac.keycloak;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author Ramu Parupalli
 * 
 * @version
 * @since
 */
@Configuration
@PropertySource("classpath:application.properties")
public class KeycloakPropertyReader {

    @Autowired
    private Environment env;

    public String getProperty(String key) {
        return env.getProperty(key);
    }

}
