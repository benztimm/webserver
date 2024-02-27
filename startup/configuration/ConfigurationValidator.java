package startup.configuration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import webserver667.exceptions.configuration.ConfigurationHelpRequestedException;
import webserver667.exceptions.configuration.InvalidServerConfigurationException;

public class ConfigurationValidator {

  private String[] args;
  private Set<ServerCliFlag> specification;
  private Map<String, String> providedArguments;
  private Set<String> errors;

  public ConfigurationValidator(String[] args, Set<ServerCliFlag> specification) {
    this.args = args;
    this.specification = specification;
    this.providedArguments = new HashMap<>();
  }

  public Set<String> validate() throws ConfigurationHelpRequestedException, InvalidServerConfigurationException {
    if (this.errors == null) {
      this.errors = new LinkedHashSet<>();

      this.validateCountAndValues();
      this.validatePresenceAndUniqueness();
    }

    return this.errors;
  }

  public Map<String, String> validatedValues() {
    return this.providedArguments;
  }

  public void validateCountAndValues() throws ConfigurationHelpRequestedException {
    int current = 0;

    while (current < this.args.length) {
      if (this.args[current].equals("-h") || this.args[current].equals("--help")) {
        throw new ConfigurationHelpRequestedException(this.specification);
      }

      if (current + 1 >= this.args.length || this.args[current + 1].startsWith("-")) {
        errors.add(String.format("A value was not provided for the flag %s", this.args[current]));
        this.providedArguments.put(this.args[current], null);

        current++;
      } else {
        this.providedArguments.put(this.args[current], this.args[current + 1]);

        current += 2;
      }
    }
  }

  public void validatePresenceAndUniqueness() throws InvalidServerConfigurationException {
    for (ServerCliFlag flagSpecification : this.specification) {
      if (!flagSpecification.isRequired() && !flagSpecification.isUnique()) {
        continue;
      }

      Iterator<String> iterator = flagSpecification.getFlags().iterator();
      int count = 0;
      Set<String> output = new LinkedHashSet<>();

      while (iterator.hasNext()) {
        String currentFlag = iterator.next();
        output.add(currentFlag);

        if (this.providedArguments.get(currentFlag) != null) {
          count++;
        }
      }

      if (flagSpecification.isUnique() && count > 1) {
        this.errors.add(
            String.format("A duplicate cli flag was detected: {%s} must be unique", String.join(", ", output)));
      }
      if (flagSpecification.isRequired() && count == 0) {
        this.errors.add(
            String.format("One of the flags {%s} is required", String.join(", ", output)));
      }
    }

    if (!this.errors.isEmpty()) {
      throw new InvalidServerConfigurationException(this.specification, this.errors);
    }
  }
}
