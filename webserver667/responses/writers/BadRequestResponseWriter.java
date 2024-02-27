package webserver667.responses.writers;

import java.io.OutputStream;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;

public class BadRequestResponseWriter extends ResponseWriter {

  public BadRequestResponseWriter(OutputStream out, IResource resource, HttpRequest httpRequest) {
    super(out, resource, httpRequest);
  }

  @Override
  public void write() {
    throw new UnsupportedOperationException("Unimplemented method 'write'");
  }

}
