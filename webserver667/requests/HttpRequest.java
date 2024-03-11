package webserver667.requests;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
  private HttpMethods httpMethod = null;
  private String uri = "";
  private String queryString;
  private String version;
  private Map<String, String> headers = new HashMap<>();
  private byte[] body;

  public HttpMethods getHttpMethod() {
    return this.httpMethod;
  }

  public void setHttpMethod(HttpMethods method) {
    this.httpMethod = method;
  }

  public String getUri() {
    return uri.split("\\?", 2)[0];
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getQueryString() {
    String[] parts = uri.split("\\?", 2);
    return parts.length > 1 ? parts[1] : null;

  }

  public void setQueryString(String queryString) {
    this.queryString = queryString;
  }

  public String getVersion() {
    return this.version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getHeader(String expectedHeaderName) {
    return this.headers.get(expectedHeaderName);
  }

  public Map<String, String> getHeaders() {
    return this.headers;
  }


  public void addHeader(String headerLine) {
    String[] headerParts = headerLine.split(":", 2);
    if (headerParts.length == 2) {
      this.headers.put(headerParts[0].trim(), headerParts[1].trim());
    }
  }

  public int getContentLength() {
    // Parse the Content-Length header to an integer if it exists
    String contentLength = getHeader("Content-Length");
    if (contentLength != null) {
      try {
        return Integer.parseInt(contentLength);
      } catch (NumberFormatException e) {
        return 0; // Or handle this error condition appropriately.
      }
    }
    return 0; // Default to 0 if header is not present or not parsable
  }

  public byte[] getBody() {
    return this.body==null ? new byte[0] :this.body;
  }

  public void setBody(byte[] body) {
    this.body = body;
  }

  public boolean hasBody() {
    return body != null && body.length > 0;
  }
}
