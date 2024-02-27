package webserver667.requests;

public class HttpRequest {

  public HttpMethods getHttpMethod() {
    return HttpMethods.DELETE;
  }

  public void setHttpMethod(HttpMethods method) {

  }

  public String getUri() {
    return null;
  }

  public void setUri(String uri) {

  }

  public String getQueryString() {
    return null;
  }

  public void setQueryString(String queryString) {

  }

  public String getVersion() {
    return null;
  }

  public void setVersion(String version) {

  }

  public String getHeader(String expectedHeaderName) {
    return null;
  }

  public void addHeader(String headerLine) {

  }

  public int getContentLength() {
    return Integer.MIN_VALUE;
  }

  public byte[] getBody() {
    return null;
  }

  public void setBody(byte[] body) {

  }

  public boolean hasBody() {
    return false;
  }
}
