package webserver667.responses.authentication;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;

public class UserPasswordAuthenticator extends UserAuthenticator {

  public UserPasswordAuthenticator(HttpRequest request, IResource resource) {
    super(request, resource);
  }

  @Override
  public boolean isAuthenticated() {
    throw new UnsupportedOperationException("Unimplemented method 'isAuthenticated'");
  }

}
