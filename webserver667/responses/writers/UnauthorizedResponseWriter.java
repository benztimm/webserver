package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;
import webserver667.requests.HttpRequest;
import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;

public class UnauthorizedResponseWriter extends ResponseWriter {
  private HttpRequest request;
  private OutputStream outStream;

  public UnauthorizedResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
    this.request = request;
    this.outStream = out;
  }

  @Override
  public void write() {
    HttpResponseCode unAuthResponse = HttpResponseCode.UNAUTHORIZED;
    this.request.getVersion();
    StringBuilder responseBuilder = new StringBuilder();
    responseBuilder.append(
        String.format("%s %d %s\r\n", this.request.getVersion(), unAuthResponse.getCode(), unAuthResponse.getReasonPhrase()));
    responseBuilder.append("WWW-Authenticate: Basic realm=\"667 Server\"\r\n");
    String response = responseBuilder.toString();
    try {
      this.outStream.write(response.getBytes());
      this.outStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
