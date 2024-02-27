package webserver667.responses.writers;

import java.io.OutputStream;
import webserver667.requests.HttpRequest;

import webserver667.responses.IResource;

public class CreatedResponseWriter extends ResponseWriter {

  public CreatedResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() {
    throw new UnsupportedOperationException("Unimplemented method 'write'");
  }

}
