package startup.configuration;

import java.util.LinkedHashSet;
import java.util.Set;

public class ServerCliFlag {
  private Set<String> flags;
  private String description;
  private boolean required;
  private boolean unique;

  public ServerCliFlag(Set<String> flags, String description, boolean required, boolean unique) {
    this.flags = flags;
    this.description = description;
    this.required = required;
    this.unique = unique;
  }

  public ServerCliFlag(Set<String> flags, String description) {
    this(flags, description, false, true);
  }

  public String getDescription() {
    return String.format(
        "%-15s %50s",
        String.join(" ", this.flags).trim(),
        String.format(
            "Required: %3s Unique: %3s\n%s%s",
            this.required ? "Yes" : "No",
            this.unique ? "Yes" : "No",
            String.format("%16s", " "),
            this.description));
  }

  public Set<String> getFlags() {
    return new LinkedHashSet<>(this.flags);
  }

  public boolean isRequired() {
    return this.required;
  }

  public boolean isUnique() {
    return this.unique;
  }
}
