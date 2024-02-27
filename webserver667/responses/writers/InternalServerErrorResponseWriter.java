package webserver667.responses.writers;

import java.io.OutputStream;
import webserver667.requests.HttpRequest;

import webserver667.responses.IResource;

public class InternalServerErrorResponseWriter extends ResponseWriter {

  public InternalServerErrorResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() {
    throw new UnsupportedOperationException("Unimplemented method 'write'");
  }

}
