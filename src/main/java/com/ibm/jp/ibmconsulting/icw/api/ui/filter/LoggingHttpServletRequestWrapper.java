package com.ibm.jp.ibmconsulting.icw.api.ui.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

@Slf4j
public class LoggingHttpServletRequestWrapper extends HttpServletRequestWrapper
    implements LoggingHttpServletWrapper {
  @Nullable private final ContentCachingRequestWrapper wrapper;
  private final String path;
  private final String method;

  public LoggingHttpServletRequestWrapper(String path, String method, HttpServletRequest request)
      throws IOException {
    super(request);
    this.wrapper =
        WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
    this.path = path;
    this.method = method;
  }

  @Override
  public LoggingContent getLoggingContent() {
    final Map<String, Object> map = new HashMap<>();

    // Header
    final Map<String, Object> headerMap = new HashMap<>();
    final Enumeration<String> headerNames = this.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      final String name = (String) headerNames.nextElement();
      final Enumeration<String> headervals = this.getHeaders(name);
      while (headervals.hasMoreElements()) {
        final String value = (String) headervals.nextElement();
        headerMap.put(name, value);
      }
    }
    map.put("header", headerMap);
    // Params
    final Map<String, Object> paramMap = new HashMap<>();
    final Enumeration<String> params = this.getParameterNames();
    while (params.hasMoreElements()) {
      final String name = (String) params.nextElement();
      final String[] values = this.getParameterValues(name);
      paramMap.put(name, String.join(",", values));
    }
    map.put("params", paramMap);

    // Path
    map.put("path", path);
    // Method
    map.put("method", method);
    // Body
    if (MediaType.APPLICATION_JSON.equals(this.getContentType())) {
      try {
        if (wrapper != null) {
          byte[] buf = wrapper.getContentAsByteArray();
          if (buf.length > 0) {
            try {
              @SuppressWarnings("unchecked")
              final Map<String, Object> bodyMap =
                  new ObjectMapper().readValue(buf, Map.class);
              map.put("body", bodyMap);
            }
            catch (final MismatchedInputException ex) {
              log.warn("bodyをmap形式に変換できません。", ex);
              map.put("body", buf);
            }
          } else {
            map.put("body", buf);
          }
        } else {
          map.put("body", "");
        }
      } catch (final IOException e) {
        log.warn("bodyの読み込みに失敗しました。", e);
      }
    }
    return new LoggingContent(path, method, RequestResponse.REQUEST, map);
  }
}
