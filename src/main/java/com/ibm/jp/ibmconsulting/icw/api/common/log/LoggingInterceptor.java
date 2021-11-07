package com.ibm.jp.ibmconsulting.icw.api.common.log;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import lombok.extern.slf4j.Slf4j;

@Interceptor
@Dependent
@Logging
@Priority(Interceptor.Priority.APPLICATION)
@Slf4j
public class LoggingInterceptor extends HandlerInterceptorAdapter {
  long startTimeEpochMilli;
  long endTimeEpochMilli;

  @Override
  public boolean preHandle(
      HttpServletRequest request, 
      HttpServletResponse response, 
      Object handler) {
    startTimeEpochMilli = getCurrentEpochMilli();
    
    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, 
      HttpServletResponse response, 
      Object handler, 
      Exception ex) {
    final HandlerMethod handlerClass = (HandlerMethod) handler;
    final String classAndMethod =
        handlerClass.getBeanType().getName() + "#" + handlerClass.getMethod().getName();
    
    endTimeEpochMilli = getCurrentEpochMilli();
    log.info("{}:{} ms", classAndMethod, endTimeEpochMilli - startTimeEpochMilli);
  }

  private static long getCurrentEpochMilli() {
    return LocalDateTime.now().atZone(ZoneOffset.ofHours(+9)).toInstant().toEpochMilli();
  }
}