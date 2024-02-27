package webserver667;

import startup.configuration.MimeTypes;
import startup.configuration.ServerConfiguration;

public class WebServer implements I667Server {

  @Override
  public void close() throws Exception {
  }

  @Override
  public void start(ServerConfiguration configuration, MimeTypes mimeTypes) {
    System.out.println(mimeTypes);
  }

  @Override
  public void stop() {
  }
}
