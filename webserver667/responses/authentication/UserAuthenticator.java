package webserver667.responses.authentication;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;

public abstract class UserAuthenticator {

  protected HttpRequest request;
  protected IResource resource;

  public UserAuthenticator(HttpRequest request, IResource resource) {
    this.request = request;
    this.resource = resource;
  }

  public abstract boolean isAuthenticated();
}
