package webserver667.responses;

import java.io.IOException;
import java.nio.file.Path;

import startup.configuration.MimeTypes;
import webserver667.requests.HttpRequest;
import webserver667.responses.authentication.UserAuthenticator;

public class Resource implements IResource {
  public Resource(String uri, String queryString, String documentRoot, MimeTypes mimeTypes) {

  }

  @Override
  public boolean exists() {
    throw new UnsupportedOperationException("Unimplemented method 'exists'");
  }

  @Override
  public Path getPath() {
    throw new UnsupportedOperationException("Unimplemented method 'getPath'");
  }

  @Override
  public boolean isProtected() {
    throw new UnsupportedOperationException("Unimplemented method 'isProtected'");
  }

  @Override
  public boolean isScript() {
    throw new UnsupportedOperationException("Unimplemented method 'isScript'");
  }

  @Override
  public UserAuthenticator getUserAuthenticator(HttpRequest request) {
    throw new UnsupportedOperationException("Unimplemented method 'getUserAuthenticator'");
  }

  @Override
  public String getMimeType() {
    throw new UnsupportedOperationException("Unimplemented method 'getMimeType'");
  }

  @Override
  public long getFileSize() throws IOException {
    throw new UnsupportedOperationException("Unimplemented method 'getFileSize'");
  }

  @Override
  public byte[] getFileBytes() throws IOException {
    throw new UnsupportedOperationException("Unimplemented method 'getFileBytes'");
  }

  @Override
  public long lastModified() {
    throw new UnsupportedOperationException("Unimplemented method 'lastModified'");
  }

}
