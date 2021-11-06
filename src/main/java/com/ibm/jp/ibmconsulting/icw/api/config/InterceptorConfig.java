package com.ibm.jp.ibmconsulting.icw.api.config;

import com.ibm.jp.ibmconsulting.icw.api.common.log.LoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterceptorConfig {

  @Bean
  public LoggingInterceptor loggingInterceptor() {
    return new LoggingInterceptor();
  }
}
