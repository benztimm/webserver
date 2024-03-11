package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;

import webserver667.logging.Logger;
import webserver667.requests.HttpRequest;
import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;

public class ForbiddenResponseWriter extends ResponseWriter {
  private HttpRequest request;
  private OutputStream outStream;
  
  public ForbiddenResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
    this.request = request;
    this.outStream = out;
  }

  @Override
  public void write() {
    HttpResponseCode forbiHttpResponse = HttpResponseCode.FORBIDDEN;
    this.request.getVersion();
    StringBuilder responseBuilder = new StringBuilder();
    responseBuilder.append(
        String.format("%s %d %s\r\n", this.request.getVersion(), forbiHttpResponse.getCode(), forbiHttpResponse.getReasonPhrase()));
    String response = responseBuilder.toString();
    try {
      this.outStream.write(response.getBytes());
      this.outStream.flush();
      System.out.println(Logger.getLogString("127.0.0.1", request, resource, forbiHttpResponse.getCode(), 0));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
