package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.authentication.UserAuthenticator;

public class ResponseWriterFactory {
  public static ResponseWriter create(OutputStream out, IResource resource, HttpRequest request) {

    ResponseWriter writer = null;
    if (resource.isProtected()) {
      UserAuthenticator userAuthenticator = resource.getUserAuthenticator(request);
      if (!userAuthenticator.isAuthenticated()) {
        if (request.getHeader("Authorization") == null) {
          writer = new UnauthorizedResponseWriter(out, resource, request);
        } else {
          writer = new ForbiddenResponseWriter(out, resource, request);
        }
        return writer;
      }
    }

    switch (request.getHttpMethod()) {
      case GET:
        if (resource.exists()) {
          String ifModifiedSinceHeader = request.getHeader("If-Modified-Since");
          SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
          dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
          if (ifModifiedSinceHeader != null) {
            try {
              Date ifModifiedSince = dateFormat.parse(ifModifiedSinceHeader);
              long lastModified = resource.lastModified();
              if (lastModified > ifModifiedSince.getTime()) {
                writer = new OkResponseWriter(out, resource, request);
              } else {
                writer = new NotModifiedResponseWriter(out, resource, request);
              }
            } catch (ParseException e) {
              e.printStackTrace();
            }

          } else {
            writer = new OkResponseWriter(out, resource, request);
          }

        } else {
          writer = new NotFoundResponseWriter(out, resource, request);
        }
        break;

      case HEAD:
        if (resource.exists()) {
          writer = new OkResponseWriter(out, resource, request);
        } else {
          writer = new NotFoundResponseWriter(out, resource, request);
        }

        break;

      case PUT:
        try {
          Path targetPath = resource.getPath();
          Files.createDirectories(targetPath.getParent());
          try (OutputStream fileOut = Files.newOutputStream(targetPath)) {
            fileOut.write(request.getBody());
          }
          boolean exists = Files.exists(targetPath);
          if (exists) {
            writer = new CreatedResponseWriter(out, resource, request);
          } else {
            writer = new InternalServerErrorResponseWriter(out, resource, request);
          }
        } catch (IOException e) {
          writer = new InternalServerErrorResponseWriter(out, resource, request);
        }

        break;
      case POST:
        if (!resource.exists()) {
          writer = new NotFoundResponseWriter(out, resource, request);
        }
        try {
          writer = new ScriptResponseWriter(out, resource, request);
        } catch (Exception e) {
          /* no op */
        }

        break;

      case DELETE:
        Path targetPath = resource.getPath();
        if (!Files.exists(targetPath)) {
          writer = new NotFoundResponseWriter(out, resource, request);
        } else {
          try {
            Files.delete(targetPath);
            writer = new NoContentResponseWriter(out, resource, request);
          } catch (IOException e) {
            writer = new InternalServerErrorResponseWriter(out, resource, request);
          }

        }
        break;
      default:
        writer = new MethodNotAllowedResponseWriter(out, resource, request);
        break;
    }
    return writer;
  }
}
