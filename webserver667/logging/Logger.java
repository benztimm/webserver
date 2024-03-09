package webserver667.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import webserver667.requests.HttpMethods;
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


    //String testUri = String.format("/%s", resource.getFileName());
    String ipString = ipAddress;
    String uri = request.getUri();
    int fileSize = bytesSent;
    String version = request.getVersion();
    String method = request.getHttpMethod().toString();

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");
    dateFormat.setTimeZone(TimeZone.getTimeZone("PST"));
    String dateTime = dateFormat.format(new Date());

    
    String outputString = ipString + " - - [" + dateTime + "] \"" + method + " " + uri + " " + version + "\" " + statusCode + " " + fileSize;
    return outputString;
  }
}
