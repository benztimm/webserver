package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;

import webserver667.logging.Logger;
import webserver667.requests.HttpRequest;
import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;

public class NotModifiedResponseWriter extends ResponseWriter {
  private HttpRequest request;
  private OutputStream outStream;

  public NotModifiedResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
    this.request = request;
    this.outStream = out;
  }

  @Override
  public void write() {
    HttpResponseCode notModifyResponse = HttpResponseCode.NOT_MODIFIED;
    this.request.getVersion();
    StringBuilder responseBuilder = new StringBuilder();
    responseBuilder.append(
        String.format("%s %d %s\r\n", this.request.getVersion(), notModifyResponse.getCode(),
            notModifyResponse.getReasonPhrase()));
    String response = responseBuilder.toString();
    try {
      this.outStream.write(response.getBytes());
      this.outStream.flush();
      System.out.println(Logger.getLogString("127.0.0.1", request, resource, notModifyResponse.getCode(), 0));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
