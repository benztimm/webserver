package webserver667.responses.authentication;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;

public class UserPasswordAuthenticator extends UserAuthenticator {
  private HttpRequest request;
  private IResource resource;

  public UserPasswordAuthenticator(HttpRequest request, IResource resource) {
    super(request, resource);
    this.request = request;
    this.resource = resource;
  }

  @Override
  public boolean isAuthenticated() {
    boolean passwordFileExists = resource.isProtected();
    String authorizationHeader = request.getHeader("Authorization");
    if(passwordFileExists && authorizationHeader==null) {
      return false;
    }

    if (!authorizationHeader.startsWith("Basic ")) {
      return false;
    }

    String encodedCredentials = authorizationHeader.split(" ", 2)[1].trim();
    Path passwordFilePath = Paths.get(this.resource.getPath().getParent().toString(), ".passwords");
    PasswordFileReader passwordFileReader;
    try {
      passwordFileReader = new PasswordFileReader(passwordFilePath);
    } catch (IOException e) {
      return false;
    }
    return passwordFileReader.isUserAuthorized(encodedCredentials);

  }

}
