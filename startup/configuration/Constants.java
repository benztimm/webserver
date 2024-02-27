package startup.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Constants {
  public static final String TESTING_MIME_CONTENT = String.join(
      "\n",
      List.of(
          "text/html\t\t\thtml htm",
          "text/css\t\t\tcss",
          "text/plain\t\t\tasc txt",
          "image/jpeg\t\t\tjpeg jpg jpe",
          "image/png\t\t\tpng",
          "image/gif\t\t\tgif",
          "application/json json",
          "application/x-javascript\tjs"));

  public static final Path MIME_TYPES_PATH = Paths.get("startup", "configuration", "mime.types");
}
