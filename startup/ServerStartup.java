package startup;

import startup.configuration.ServerConfiguration;
import webserver667.I667Server;
import webserver667.WebServer;
import webserver667.exceptions.configuration.ConfigurationHelpRequestedException;
import webserver667.exceptions.configuration.InvalidServerConfigurationException;

public class ServerStartup {
  public static void main(String[] args) {
    try (I667Server server = new WebServer()) {
      ServerConfiguration config = ServerConfiguration.getDefault(args);

      server.start(config, config.getMimeFileContent());
    } catch (ConfigurationHelpRequestedException configHelpException) {
      System.out.println(configHelpException.getMessage());
      System.exit(0);
    } catch (InvalidServerConfigurationException configException) {
      System.out.println(configException.getMessage());
      System.exit(1);
    } catch (Exception failedToCloseException) {
      System.err.println("Failed to close server resources on shut down");
      System.exit(1);
    }
  }
}