package startup.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import webserver667.exceptions.configuration.ConfigurationHelpRequestedException;
import webserver667.exceptions.configuration.InvalidServerConfigurationException;

public class ServerConfiguration {
  protected static final ServerCliFlag helpFlag = new ServerCliFlag(
      new LinkedHashSet<String>(Arrays.asList("-h", "--help")),
      "Print usage information");
  protected static final ServerCliFlag portFlag = new ServerCliFlag(
      new LinkedHashSet<String>(Arrays.asList("-p", "--port")),
      "The integer port number the server should listen on",
      true,
      true);
  protected static final ServerCliFlag documentRootFlag = new ServerCliFlag(
      new LinkedHashSet<String>(Arrays.asList("-r", "--root")),
      "The local filesystem path from which the server should resolve URIs",
      true,
      true);
  protected static final ServerCliFlag mimeFlag = new ServerCliFlag(
      new LinkedHashSet<String>(Arrays.asList("-m", "--mimes")),
      "An optional string providing mime types (instead of using a file)",
      false,
      true);

  public static ServerConfiguration getDefault(String[] args)
      throws InvalidServerConfigurationException, ConfigurationHelpRequestedException {

    return new ServerConfiguration(args,
        new LinkedHashSet<ServerCliFlag>(Arrays.asList(helpFlag, portFlag, documentRootFlag, mimeFlag)));
  }

  private Set<ServerCliFlag> specification;
  private int port;
  private Path documentRootPath;
  private MimeTypes mimeFileContent;

  public ServerConfiguration(String[] args, Set<ServerCliFlag> cliFlagSpecification)
      throws InvalidServerConfigurationException, ConfigurationHelpRequestedException {

    this.specification = cliFlagSpecification;

    ConfigurationValidator validator = new ConfigurationValidator(args, cliFlagSpecification);
    validator.validate();

    Map<String, String> validatedValues = validator.validatedValues();

    this.setPort(validatedValues.get("-p"), validatedValues.get("--port"));
    this.setDocumentRootPath(validatedValues.get("-r"), validatedValues.get("--root"));
    this.setMimeFileContent(validatedValues.get("-m"), validatedValues.get("--mimes"));
  }

  private void setPort(String shortVersion, String longVersion) throws InvalidServerConfigurationException {
    if (!this.specification.contains(portFlag)) {
      return;
    }

    String specifiedVersion = shortVersion == null ? longVersion : shortVersion;

    try {
      this.port = Integer.parseInt(specifiedVersion);
    } catch (NumberFormatException exception) {
      throw new InvalidServerConfigurationException(
          this.specification,
          new LinkedHashSet<>(Arrays.asList(String.format("The value %s is not valid for port", specifiedVersion))));
    }
  }

  public int getPort() {
    return this.port;
  }

  private void setDocumentRootPath(String shortVersion, String longVersion) throws InvalidServerConfigurationException {
    if (!this.specification.contains(documentRootFlag)) {
      return;
    }

    String specifiedVersion = shortVersion == null ? longVersion : shortVersion;

    this.documentRootPath = Paths.get(specifiedVersion);

    if (!Files.exists(this.documentRootPath)) {
      throw new InvalidServerConfigurationException(
          this.specification,
          new LinkedHashSet<>(
              Arrays.asList(String.format("The path %s does not exist", this.documentRootPath.toString()))));
    }
  }

  public Path getDocumentRoot() {
    return this.documentRootPath;
  }

  private void setMimeFileContent(String shortVersion, String longVersion) throws InvalidServerConfigurationException {
    if (this.specification.contains(mimeFlag) && shortVersion != null && longVersion != null) {
      this.mimeFileContent = new MimeTypes(shortVersion != null ? shortVersion : longVersion);
    } else {
      try {
        this.mimeFileContent = MimeTypes.fromDefaultFile();
      } catch (IOException e) {
        throw new InvalidServerConfigurationException(this.specification,
            new LinkedHashSet<>(
                Arrays.asList(String.format("Unable to read the default mime file %s", Constants.MIME_TYPES_PATH))));
      }
    }
  }

  public MimeTypes getMimeFileContent() {
    return this.mimeFileContent;
  }
}
