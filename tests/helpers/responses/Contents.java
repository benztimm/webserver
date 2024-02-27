package tests.helpers.responses;

public class Contents {
  /**
   * This default is for jrob's machine; you will need to
   * set an environment variable named 667_TEST_NODE_PATH
   * to test on your machine
   */
  public static String TEST_NODE_PATH_667 = "/opt/homebrew/bin/node";

  public static String HTML_FILE = String.join(
      System.lineSeparator(),
      "<html>",
      "  <head><title>Test Content</title></head>",
      "  <body><h1>Hello</h1><b>Test</b> <i>file</i> <em>generated</em> in test environment.</body>",
      "</html>");

  public static String NODE_SCRIPT_FILE = String.join(
      System.lineSeparator(),
      String.format("#!%s", getNodePathFromEnvOrDefault()),
      "const content = `Hello world!`",
      "process.stdout.write('Content-Type: text/html\\r\\n');",
      "process.stdout.write(`Content-Length: ${content.length}\\r\\n`);",
      "process.stdout.write('\\r\\n');",
      "process.stdout.write(content);");

  private static String getNodePathFromEnvOrDefault() {
    String possible = System.getenv("667_TEST_NODE_PATH");

    if (possible == null) {
      return TEST_NODE_PATH_667;
    } else {
      return possible;
    }
  }
}
