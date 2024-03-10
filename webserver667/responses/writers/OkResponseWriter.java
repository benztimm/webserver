package webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import webserver667.requests.HttpRequest;
import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;

public class OkResponseWriter extends ResponseWriter {
  private HttpRequest request;
  private OutputStream outStream;
  private IResource resource;

  public OkResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
    this.request = request;
    this.outStream = out;
    this.resource = resource;

  }

  @Override
  public void write() {
    HttpResponseCode okResponse = HttpResponseCode.OK;
    this.request.getVersion();
    StringBuilder responseBuilder = new StringBuilder();

    try {
      responseBuilder.append(
          String.format("%s %d %s\r\n", this.request.getVersion(), okResponse.getCode(), okResponse.getReasonPhrase()));
      responseBuilder.append(String.format("Content-Type: %s\r\n", this.resource.getMimeType()));
      responseBuilder.append(String.format("Content-Length: %d\r\n", this.resource.getFileSize()));
      responseBuilder.append("\r\n"); 
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    String response = responseBuilder.toString();
    try {
      this.outStream.write(response.getBytes());
      this.outStream.write(this.resource.getFileBytes());
      this.outStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
