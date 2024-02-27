package webserver667;

import startup.configuration.MimeTypes;
import startup.configuration.ServerConfiguration;

/**
 * An interface to group the AutoCloseable functionality with the
 * functionality required for starting the webserver.
 */
public interface I667Server extends AutoCloseable {
  public void start(ServerConfiguration configuration, MimeTypes mimeTypes);

  public void stop();
}
