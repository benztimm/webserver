package webserver667.requests;


import java.io.IOException;
import java.io.InputStream;

import webserver667.exceptions.responses.BadRequestException;
import webserver667.exceptions.responses.MethodNotAllowedException;

public class RequestReader {
  private final InputStream input;

  public RequestReader(InputStream input) {
    this.input = input;
  }

  public String readLine(InputStream input) throws IOException {
    StringBuilder line = new StringBuilder();
    int c;
    while ((c = input.read()) != -1) {
      if (c == '\r') { 
        input.mark(1); 
        int nextChar = input.read();
        if (nextChar != '\n') {
          input.reset(); 
        }
        break; 
      } else {
        line.append((char) c);
      }
    }
    return line.toString();
  }

  public HttpRequest getRequest() throws BadRequestException, MethodNotAllowedException {
    HttpRequest request = new HttpRequest();

    try {
      String requestLine = readLine(this.input);
      if (requestLine == null || requestLine.isEmpty()) {
        throw new BadRequestException("Request line is empty");
      }

      String[] requestParts = requestLine.split("\\s+");
      if (requestParts.length < 3) {
        throw new BadRequestException("Invalid request line format");
      }
      try {
        request.setHttpMethod(HttpMethods.valueOf(requestParts[0]));
      } catch (IllegalArgumentException e) {
        throw new MethodNotAllowedException("Method " + requestParts[0] + " not allowed");
      }

      request.setUri(requestParts[1]);
      request.setVersion(requestParts[2]);

      String headerLine;
      while ((headerLine = readLine(input)) != null && !headerLine.isEmpty()) {
        request.addHeader(headerLine);
      }

      int contentLength = request.getContentLength();
      if (contentLength > 0) {
        byte[] body = new byte[contentLength];
        int totalBytesRead = 0;
        int bytesRead;
        while (totalBytesRead < contentLength) {
          bytesRead = input.read(body, totalBytesRead, contentLength - totalBytesRead);
          if (bytesRead == -1) {
            break;
          }
          totalBytesRead += bytesRead;
        }
        if (totalBytesRead != contentLength) {
          throw new BadRequestException("Actual body length does not match Content-Length header");
        }
        request.setBody(body);
      }

    } catch (IOException e) {
      throw new BadRequestException("Error reading the request", e);
    }

    return request;
  }
}
