package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;
import webserver667.requests.HttpRequest;
import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;

public class CreatedResponseWriter extends ResponseWriter {
  private HttpRequest request;
  private OutputStream outStream;
  public CreatedResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
    this.request = request;
    this.outStream = out;
  }

  @Override
  public void write() {
    HttpResponseCode createResponse = HttpResponseCode.CREATED;
    this.request.getVersion();
    StringBuilder responseBuilder = new StringBuilder();
    responseBuilder.append(
        String.format("%s %d %s\r\n", this.request.getVersion(), createResponse.getCode(), createResponse.getReasonPhrase()));
    String response = responseBuilder.toString();
    try {
      this.outStream.write(response.getBytes());
      this.outStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
