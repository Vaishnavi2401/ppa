package com.cdac.feedback.multitenant;

import java.util.HashMap;
import java.util.Objects;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;

public class LazyMultitenantMap extends HashMap<Object, DataSource> {

  private static final long serialVersionUID = 8218076749298902802L;

  private static final Logger log = LoggerFactory.getLogger(LazyMultitenantMap.class);
  
  private String defaultTenant;
  
  private DataSourceProperties properties;
  String url;

  public LazyMultitenantMap(String defaultTenant, DataSourceProperties properties) {
    this.defaultTenant = defaultTenant;
    this.properties = properties;
  }
  
  @Override
  public DataSource get(Object key) {
    DataSource source = super.get(key);
    if (Objects.isNull(source) && Objects.nonNull(key)) {
      source = buildDataSource((String)key);
      super.put(key,source);      
    }
    return source;
  }
  
  public DataSource getDefaultDataSource() {
    return get(defaultTenant);
  }
  
  private DataSource buildDataSource(String tenant) {
    
    String userName = properties.getUsername();
    String password = properties.getPassword();
    
    if(isBlank(userName) || isBlank(password) || isBlank(tenant)) {
      log.info("Incomplete tenant information for [" +  tenant + "]");
      return null;
    }
    if(tenant.equalsIgnoreCase("elearn_saas_application"))
     url = String.format("%s%s", properties.getUrl(),tenant);
    else
    url = String.format("%s%s%s", properties.getUrl(), "megh_feedback_",tenant);
    System.out.println("url---"+url);
    DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create(this.getClass().getClassLoader())
        .driverClassName(properties.getDriverClassName())
        .url(url)
        .username(userName)
        .password(password);
    
    if (properties.getType() != null) {
      dataSourceBuilder.type(properties.getType());
    }
    return dataSourceBuilder.build();
  }
  
  private boolean isBlank(String input) {
    return Objects.nonNull(input) && input.isEmpty();
  }

}
