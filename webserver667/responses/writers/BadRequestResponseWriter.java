package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;

import webserver667.logging.Logger;
import webserver667.requests.HttpRequest;
import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;

public class BadRequestResponseWriter extends ResponseWriter {

  private HttpRequest request;
  private OutputStream outStream;

  public BadRequestResponseWriter(OutputStream out, IResource resource, HttpRequest httpRequest) {
    super(out, resource, httpRequest);
    this.request = httpRequest;
    this.outStream = out;
  }

  @Override
  public void write() {
    HttpResponseCode badRequest = HttpResponseCode.BAD_REQUEST;
    this.request.getVersion();
    StringBuilder responseBuilder = new StringBuilder();
    responseBuilder.append(
        String.format("%s %d %s\r\n", this.request.getVersion(), badRequest.getCode(), badRequest.getReasonPhrase()));
    String response = responseBuilder.toString();
    try {
      this.outStream.write(response.getBytes());
      this.outStream.flush();
      System.out.println(Logger.getLogString("127.0.0.1", request, resource, badRequest.getCode(), 0));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
