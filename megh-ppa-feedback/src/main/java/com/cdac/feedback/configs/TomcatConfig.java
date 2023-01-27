package com.cdac.feedback.configs;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {
    
    private static final String PROTOCOL = "AJP/1.3";

    @Value("${tomcat.ajp.port}")
    int ajpPort;

    @Value("${tomcat.ajp.secret}")
    String ajpSecret;

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
      return server -> {
        if (server instanceof TomcatServletWebServerFactory) {
            ((TomcatServletWebServerFactory) server).addAdditionalTomcatConnectors(redirectConnector());
        }
      };
    }

    private Connector redirectConnector() {
    
      
      final Connector connector = new Connector("AJP/1.3");
      connector.setScheme("http");
      connector.setPort(ajpPort);
      connector.setAllowTrace(false);

      final AbstractAjpProtocol protocol = (AbstractAjpProtocol) connector.getProtocolHandler();
      connector.setSecure(false);
      protocol.setSecretRequired(false);
      
      
      
       return connector;
    }
    
    
    
}
