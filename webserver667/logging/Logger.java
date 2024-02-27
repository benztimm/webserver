package webserver667.logging;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;

public class Logger {
  /**
   * Your implementation for logging to stdout should be placed here
   */
  public static String getLogString(
      String ipAddress,
      HttpRequest request,
      IResource resource,
      int statusCode,
      int bytesSent) {
    return "";
  }
}
