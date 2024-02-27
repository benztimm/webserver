package webserver667.responses.writers;

import java.io.OutputStream;

import webserver667.exceptions.responses.ServerErrorException;
import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;

public abstract class ResponseWriter {
  protected IResource resource;
  protected OutputStream outputStream;
  protected HttpRequest request;

  public ResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    this.outputStream = out;
    this.resource = resource;
    this.request = request;
  }

  public abstract void write() throws ServerErrorException;
}
