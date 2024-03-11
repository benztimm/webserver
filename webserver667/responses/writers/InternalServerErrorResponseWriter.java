package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;

import webserver667.logging.Logger;
import webserver667.requests.HttpRequest;
import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;

public class InternalServerErrorResponseWriter extends ResponseWriter {
  private HttpRequest request;
  private OutputStream outStream;

  public InternalServerErrorResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
    this.request = request;
    this.outStream = out;
  }

  @Override
  public void write() {
    HttpResponseCode internalSeverErrResponseCode = HttpResponseCode.INTERNAL_SERVER_ERROR;
    this.request.getVersion();
    StringBuilder responseBuilder = new StringBuilder();
    responseBuilder.append(
        String.format("%s %d %s\r\n", this.request.getVersion(), internalSeverErrResponseCode.getCode(), internalSeverErrResponseCode.getReasonPhrase()));
    String response = responseBuilder.toString();
    try {
      this.outStream.write(response.getBytes());
      this.outStream.flush();
      System.out.println(Logger.getLogString("127.0.0.1", request, resource, internalSeverErrResponseCode.getCode(), 0));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
