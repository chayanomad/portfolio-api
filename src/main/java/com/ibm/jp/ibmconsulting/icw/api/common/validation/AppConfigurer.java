package com.ibm.jp.ibmconsulting.icw.api.common.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfigurer implements WebMvcConfigurer {
  @Bean(name="validator")
  public LocalValidatorFactoryBean localValidatorFactoryBean() {
    LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
    localValidatorFactoryBean.setValidationMessageSource(messageSource());
    return localValidatorFactoryBean;
  }

  @Bean(name = "messageSource")
  public MessageSource messageSource()
  {
      ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
      bean.setBasename("ValidationMessages.properties");
      bean.setDefaultEncoding("UTF-8");
      return bean;
  }
}
