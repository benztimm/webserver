package startup.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MimeTypes {

  public static MimeTypes fromDefaultFile() throws IOException {
    return new MimeTypes(
        String.join(
            System.lineSeparator(),
            Files.readAllLines(Constants.MIME_TYPES_PATH)));
  }

  private Map<String, String> typeMap;

  public MimeTypes(String sourceContent) {
    this.typeMap = new HashMap<>();

    Arrays.asList(sourceContent.split(System.lineSeparator())).stream()
        .filter(line -> line.trim().length() > 0 && !line.startsWith("#"))
        .forEach(line -> {
          String[] lineParts = line.split("\\s+");

          if (lineParts.length > 1) {
            for (int index = 1; index < lineParts.length; index++) {
              typeMap.put(lineParts[index], lineParts[0]);
            }
          }
        });
  }

  public String getMimeTypeByExtension(String extension) {
    return this.typeMap.get(extension);
  }

  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();

    this.typeMap.entrySet().stream().sorted(Comparator.comparing(entry -> entry.getKey())).forEach(entry -> {
      buffer.append(String.format("%-8s %s\n", entry.getKey(), entry.getValue()));
    });

    return buffer.toString();
  }

  public static void main(String[] args) throws IOException {
    MimeTypes mimeTypes = MimeTypes.fromDefaultFile();
    System.out.println(
        mimeTypes);
  }
}
