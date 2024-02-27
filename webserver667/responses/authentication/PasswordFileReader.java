package webserver667.responses.authentication;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class PasswordFileReader {
  private Map<String, String> users;

  public PasswordFileReader(Path passwordFilePath) throws IOException {
    users = new HashMap<>();

    Files.readAllLines(passwordFilePath).stream().forEach(line -> {
      String[] parts = line.replace("{SHA-1}", "").split(":");

      users.put(parts[0], parts[1]);
    });
  }

  public boolean isUserAuthorized(String base64EncodedString) {
    String credentials = new String(
        Base64.getDecoder().decode(base64EncodedString),
        Charset.forName("UTF-8"));

    // Username and password from the client
    String[] parts = credentials.split(":");
    // SHA-1 encrypted password
    return users.get(parts[0]) == encryptClearPassword(parts[1]);
  }

  private String encryptClearPassword(String password) {
    try {
      MessageDigest mDigest = MessageDigest.getInstance("SHA-1");
      byte[] result = mDigest.digest(password.getBytes());

      return Base64.getEncoder().encodeToString(result);
    } catch (Exception e) {
      return "";
    }
  }
}