package com.ibm.jp.ibmconsulting.icw.api.ui.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class LoggingHttpServletResponseWrapper extends HttpServletResponseWrapper
    implements LoggingHttpServletWrapper {

  private final LoggingServletOutputStream outputStream = new LoggingServletOutputStream();
  private final String path;
  private final String method;

  public LoggingHttpServletResponseWrapper(
      String path, String method, HttpServletResponse response) {
    super(response);
    this.path = path;
    this.method = method;
  }

  @Override
  public ServletOutputStream getOutputStream() {
    return outputStream;
  }

  @Override
  public PrintWriter getWriter() {
    return new PrintWriter(outputStream.baos);
  }

  public String getBody() {
    try {
      return outputStream.baos.toString(Charset.forName("UTF-8").toString());
    } catch (UnsupportedEncodingException e) {
      log.warn("unsupported encoding content.", e);
      return "[[unsupported encoding content]]";
    }
  }

  public byte[] getBodyAsBytes() {
    return outputStream.baos.toByteArray();
  }

  @Override
  public LoggingContent getLoggingContent() {
    final Map<String, Object> map = new HashMap<>();

    // status
    map.put("status", this.getStatus());

    // Header
    final Map<String, Object> headerMap = new HashMap<>();
    for (final String name : this.getHeaderNames()) {
      for (final String value : this.getHeaders(name)) {
        headerMap.put(name, value);
      }
    }
    map.put("headers", headerMap);

    // body
    final String body =
        MediaType.APPLICATION_JSON.equals(this.getContentType()) ? this.getBody() : null;
    if (StringUtils.isNotBlank(body)) {
      try {
        @SuppressWarnings("unchecked")
        final Map<String, Object> bodyMap = new ObjectMapper().readValue(this.getBody(), Map.class);
        map.put("body", bodyMap);

      } catch (final IOException e) {
        log.warn("bodyの読み込みに失敗しました。{}", e);
      }
    } else {
      map.put("body", body);
    }
    return new LoggingContent(path, method, RequestResponse.RESPONSE, map);
  }

  private static class LoggingServletOutputStream extends ServletOutputStream {

    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
      // not used
    }

    @Override
    public void write(int bytes) {
      baos.write(bytes);
    }

    @Override
    public void write(byte[] bytes) throws IOException {
      baos.write(bytes);
    }

    @Override
    public void write(byte[] bytes, int offset, int length) {
      baos.write(bytes, offset, length);
    }
  }
}
