package webserver667.responses;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import startup.configuration.MimeTypes;
import webserver667.requests.HttpMethods;
import webserver667.requests.HttpRequest;
import webserver667.responses.authentication.UserAuthenticator;
import webserver667.responses.authentication.UserPasswordAuthenticator;

public class Resource implements IResource {
  private Path path;
  private MimeTypes mimeTypes;
  private String uri;
  private HttpRequest request;


  public Resource(HttpRequest request, String documentRoot, MimeTypes mimeTypes) {    
    this.mimeTypes = mimeTypes;
    this.uri = request.getUri();
    this.path = Paths.get(documentRoot.toString(), this.uri.toString()).normalize();
    this.request = request;
  }

  @Override
  public boolean exists() {
    return Files.exists(this.path);
  }

  @Override
  public Path getPath() {
    return this.path;
  }

  @Override
  public boolean isProtected() {
    return Files.exists(Paths.get(this.path.getParent().toString(),".passwords"));
  }

  @Override
  public boolean isScript() {
    return this.request.getHttpMethod()==HttpMethods.POST;
  }

  @Override
  public UserAuthenticator getUserAuthenticator(HttpRequest request) {
    return new UserPasswordAuthenticator(request, this);
  }

  @Override
  public String getMimeType() {
    String fileName = path.getFileName().toString();
    int i = fileName.lastIndexOf('.');
    if (i > 0) {
      String extension = fileName.substring(i + 1);
      return mimeTypes.getMimeTypeByExtension(extension);
    }
    return "text/plain";
  }

  @Override
  public long getFileSize() throws IOException {
    return Files.size(path);
  }

  @Override
  public byte[] getFileBytes() throws IOException {
    return Files.readAllBytes(path);
  }

  @Override
  public long lastModified() {
    return path.toFile().lastModified();
  }

}
