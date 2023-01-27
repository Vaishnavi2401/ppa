package com.cdac.feedback.multitenant;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Lazy
@Configuration
@ConditionalOnProperty({ "cdac.multitenant.defaultTenant" })
public class MultitenantConfiguration {

  @Autowired
  private DataSourceProperties properties;

  @Value("${cdac.multitenant.defaultTenant}")
  private String defaultTenant;

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource() {
    LazyMultitenantMap map = new LazyMultitenantMap(defaultTenant, properties);
    MultitenantDataSource dataSource = new MultitenantDataSource(map);
    return dataSource;
  }

}
