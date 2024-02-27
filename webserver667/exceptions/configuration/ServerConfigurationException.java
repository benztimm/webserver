package webserver667.exceptions.configuration;

import java.util.Set;

import startup.configuration.ServerCliFlag;

public abstract class ServerConfigurationException extends Exception {
  public ServerConfigurationException(String message) {
    super(message);
  }

  public static String generateUsageInformation(Set<ServerCliFlag> cliFlagSpecification) {
    StringBuffer buffer = new StringBuffer();

    buffer.append("Usage instructions:\n");
    for (ServerCliFlag flag : cliFlagSpecification) {
      buffer.append(String.format("%s\n", flag.getDescription()));
    }

    return buffer.toString();
  }
}
