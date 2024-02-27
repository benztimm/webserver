package webserver667.responses;

import java.io.IOException;
import java.nio.file.Path;

import webserver667.requests.HttpRequest;
import webserver667.responses.authentication.UserAuthenticator;

public interface IResource {
  public boolean exists();

  public Path getPath();

  public boolean isProtected();

  public boolean isScript();

  public UserAuthenticator getUserAuthenticator(HttpRequest request);

  public String getMimeType();

  public long getFileSize() throws IOException;

  public byte[] getFileBytes() throws IOException;

  public long lastModified();
}