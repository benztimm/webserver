package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;

import webserver667.exceptions.responses.ServerErrorException;
import webserver667.logging.Logger;
import webserver667.requests.HttpMethods;
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
  public void write() throws ServerErrorException {
    HttpResponseCode okResponse = HttpResponseCode.OK;
    this.request.getVersion();
    StringBuilder responseBuilder = new StringBuilder();

    try {
        responseBuilder.append(String.format("%s %d %s\r\n", this.request.getVersion(), okResponse.getCode(), okResponse.getReasonPhrase()));
        responseBuilder.append(String.format("Content-Type: %s\r\n", this.resource.getMimeType()));
        responseBuilder.append(String.format("Content-Length: %d\r\n", this.resource.getFileSize()));
        
        
        responseBuilder.append("\r\n");

        this.outStream.write(responseBuilder.toString().getBytes());

        if (!this.request.getHttpMethod().equals(HttpMethods.HEAD)) {
            this.outStream.write(this.resource.getFileBytes());
            System.out.println(Logger.getLogString("127.0.0.1", request, resource, okResponse.getCode(), this.resource.getFileBytes().length));
        }else{
          System.out.println(Logger.getLogString("127.0.0.1", request, resource, okResponse.getCode(), 0));
        }

        this.outStream.flush();

    } catch (IOException e) {
        throw new ServerErrorException(e.getMessage());
    }
  }
}
