package webserver667.exceptions.configuration;

import java.util.Set;

import startup.configuration.ServerCliFlag;

public class InvalidServerConfigurationException extends ServerConfigurationException {
  public InvalidServerConfigurationException(Set<ServerCliFlag> cliFlagSpecification, Set<String> errors) {
    super(String.format(
        "Please correct the following errors: %s\n\n%s",
        String.join("\n", errors),
        ServerConfigurationException.generateUsageInformation(cliFlagSpecification)));
  }
}