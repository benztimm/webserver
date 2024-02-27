package webserver667.exceptions.configuration;

import java.util.Set;

import startup.configuration.ServerCliFlag;

public class ConfigurationHelpRequestedException extends ServerConfigurationException {
  public ConfigurationHelpRequestedException(Set<ServerCliFlag> cliFlagSpecification) {
    super(ServerConfigurationException.generateUsageInformation(cliFlagSpecification));
  }
}
