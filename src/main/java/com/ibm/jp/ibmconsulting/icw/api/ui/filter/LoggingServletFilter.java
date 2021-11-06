package com.ibm.jp.ibmconsulting.icw.api.ui.filter;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoggingServletFilter implements Filter {

  @Override
  public void doFilter(
      final ServletRequest request, final ServletResponse response, final FilterChain chain)
      throws ServletException, IOException {

    MDC.put("ID", UUID.randomUUID().toString());

    final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    final String path = httpServletRequest.getServletPath();
    final String method = httpServletRequest.getMethod();

    final LoggingHttpServletRequestWrapper req =
        new LoggingHttpServletRequestWrapper(path, method, httpServletRequest);
    final LoggingHttpServletResponseWrapper res =
        new LoggingHttpServletResponseWrapper(path, method, httpServletResponse);
    log.info(req.getLoggingContent().toString());

    chain.doFilter(req, res);

    log.info(res.getLoggingContent().toString());

    response.getOutputStream().write(res.getBodyAsBytes());
  }
}
