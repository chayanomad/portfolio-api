package com.ibm.jp.ibmconsulting.icw.api.common.log;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ibm.jp.ibmconsulting.icw.api.ui.filter.LoggingHttpServletRequestWrapper;
import com.ibm.jp.ibmconsulting.icw.api.ui.filter.LoggingHttpServletResponseWrapper;
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

    // final String path = request.getServletPath();
    // final String method = request.getMethod();
    // try {
    //   final LoggingHttpServletRequestWrapper req =
    //       new LoggingHttpServletRequestWrapper(path, method, request);
    //   log.info(req.getLoggingContent().toString());
    // } catch (final IOException e) {
    //   log.warn("入力の読み込みに失敗しました。", e);
    // }
    
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

    // final String path = request.getServletPath();
    // final String method = request.getMethod();
    // final LoggingHttpServletResponseWrapper res =
    //     new LoggingHttpServletResponseWrapper(path, method, response);
    // log.info(res.getLoggingContent().toString());
  }

  private static long getCurrentEpochMilli() {
    return LocalDateTime.now().atZone(ZoneOffset.ofHours(+9)).toInstant().toEpochMilli();
  }
}