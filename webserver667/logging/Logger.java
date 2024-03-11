package webserver667.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;

public class Logger {
  public static String getLogString(
      String ipAddress,
      HttpRequest request,
      IResource resource,
      int statusCode,
      int bytesSent) {


    String ipString = ipAddress;
    String uri = request.getUri();
    int fileSize = bytesSent;
    String version = request.getVersion();
    String method = request.getHttpMethod()==null?"":request.getHttpMethod().toString();

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    String dateTime = dateFormat.format(new Date());

    
    StringBuilder logString = new StringBuilder(String.format("%s - - [%s] \"%s %s %s\" %d %d", ipString, dateTime, method, uri, version, statusCode, fileSize));
    return logString.toString();
  }
}
