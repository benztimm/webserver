package webserver667.responses.writers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import webserver667.exceptions.responses.ServerErrorException;
import webserver667.requests.HttpRequest;

import webserver667.responses.IResource;

public class ScriptResponseWriter extends ResponseWriter {
  OutputStream out;
  IResource resource;
  HttpRequest request;

  public ScriptResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
    this.out = out;
    this.resource = resource;
    this.request = request;
  }

  private List<String> getCommandWithInterpreter(String scriptPath) throws IOException, ServerErrorException {
    String firstLine;
    try (BufferedReader br = new BufferedReader(
        new InputStreamReader(Files.newInputStream(Paths.get(scriptPath)), StandardCharsets.UTF_8))) {
      firstLine = br.readLine();
    }

    List<String> command = new ArrayList<>();
    if (firstLine != null && firstLine.startsWith("#!")) {
      String interpreter;
      if (firstLine.contains("node")) {
        interpreter = "node";
      } else {
        interpreter = firstLine.substring(2).trim();
      }
      command.add(interpreter);
    } else {
      ResponseWriter responseWriter = new InternalServerErrorResponseWriter(out, resource, request);
      responseWriter.write();
      throw new ServerErrorException("No shebang line found in script");
    }
    command.add(scriptPath);
    return command;
  }

  @Override
  public void write() throws ServerErrorException {
    String scriptPath = resource.getPath().toString();
    List<String> command;
    try {
      command = getCommandWithInterpreter(scriptPath);
      ProcessBuilder processBuilder = new ProcessBuilder(command);
      Map<String, String> env = processBuilder.environment();

      // Set environment variables
      request.getHeaders().forEach((key, value) -> env.put("HTTP_" + key.toUpperCase().replace("-", "_"), value));
      env.put("SERVER_PROTOCOL", request.getVersion());
      if (request.getQueryString() != null) {
        env.put("QUERY_STRING", request.getQueryString());
      }
      Process process = processBuilder.start();
      BufferedReader reader = new BufferedReader(
          new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
      String output = reader.lines().collect(Collectors.joining("\r\n"));
      this.out.write("HTTP/1.1 200 OK\r\n".getBytes());
      this.out.write(output.getBytes());

    } catch (IOException e) {
      ResponseWriter responseWriter = new InternalServerErrorResponseWriter(out, resource, request);
      responseWriter.write();
      throw new ServerErrorException(e.getMessage());
    }
  }

}
