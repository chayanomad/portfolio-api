package com.ibm.jp.ibmconsulting.icw.api.ui.filter;

import java.util.Map;
import lombok.Getter;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@Getter
public class LoggingContent {
  private final String path;
  private final String method;
  private final RequestResponse requestResponse;
  private final Map<String, Object> content;

  public LoggingContent(
      String path, String method, RequestResponse requestResponse, Map<String, Object> content) {
    this.path = path;
    this.method = method;
    this.content = content;
    this.requestResponse = requestResponse;
  }

  @Override
  public String toString() {
    return requestResponse.name() + ":" + content.toString();
  }
}
