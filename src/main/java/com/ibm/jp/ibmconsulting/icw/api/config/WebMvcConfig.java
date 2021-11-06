package com.ibm.jp.ibmconsulting.icw.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private final HandlerInterceptor interceptor;

  public WebMvcConfig(HandlerInterceptor loggingInterceptor) {
    this.interceptor = loggingInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(this.interceptor);
  }
}
