package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;

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
    
        responseBuilder.append("Content-Type: text/html; charset=UTF-8\r\n");
        responseBuilder.append("\r\n");
        responseBuilder.append("<html><body><h1>400 Bad Request</h1><p>Your browser sent a request that this server could not understand.</p></body></html>");
    String response = responseBuilder.toString();
    System.out.println(response);
    try {
      this.outStream.write(response.getBytes());
      this.outStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
