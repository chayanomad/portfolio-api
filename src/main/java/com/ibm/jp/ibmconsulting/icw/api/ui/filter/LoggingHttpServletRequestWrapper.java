package com.ibm.jp.ibmconsulting.icw.api.ui.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class LoggingHttpServletRequestWrapper extends HttpServletRequestWrapper
    implements LoggingHttpServletWrapper {
  private final byte[] content;
  private final HttpServletRequest delegate;
  private final String path;
  private final String method;

  public LoggingHttpServletRequestWrapper(String path, String method, HttpServletRequest request)
      throws IOException {
    super(request);
    this.delegate = request;
    this.path = path;
    this.method = method;
    content = IOUtils.toByteArray(delegate.getInputStream());
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    if (ArrayUtils.isEmpty(content)) {
      return delegate.getInputStream();
    }
    return new LoggingServletInputStream(content);
  }

  public String getContent() {
    return new String(content, Charset.forName("UTF-8"));
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
      final String value = this.getParameter(name);
      paramMap.put(name, value);
    }
    map.put("params", paramMap);

    // Path
    map.put("path", path);
    // Method
    map.put("method", method);
    // Body
    if (MediaType.APPLICATION_JSON.equals(this.getContentType())) {
      try {
        final String body = this.getContent();
        if (StringUtils.isNotBlank(body)) {
          try {
            @SuppressWarnings("unchecked")
            final Map<String, Object> bodyMap =
                new ObjectMapper().readValue(this.getContent(), Map.class);
            map.put("body", bodyMap);
          } catch (final MismatchedInputException mie) {
            log.warn("bodyをmap形式に変換できません。", mie);
            map.put("body", body);
          }
        } else {
          map.put("body", body);
        }

      } catch (final IOException e) {
        log.warn("bodyの読み込みに失敗しました。", e);
      }
    }
    return new LoggingContent(path, method, RequestResponse.REQUEST, map);
  }

  private static class LoggingServletInputStream extends ServletInputStream {

    private final InputStream is;

    private LoggingServletInputStream(byte[] content) {
      super();
      this.is = new ByteArrayInputStream(content);
    }

    @Override
    public boolean isFinished() {
      return true;
    }

    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
      // not used
    }

    @Override
    public int read() throws IOException {
      return this.is.read();
    }

    @Override
    public void close() throws IOException {
      super.close();
      is.close();
    }
  }
}
