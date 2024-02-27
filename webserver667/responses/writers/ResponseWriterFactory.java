package webserver667.responses.writers;

import java.io.OutputStream;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;

public class ResponseWriterFactory {
  public static ResponseWriter create(OutputStream out, IResource resource, HttpRequest request) {
    return null;
  }
}
