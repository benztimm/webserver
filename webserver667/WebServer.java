package webserver667;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import startup.configuration.MimeTypes;
import startup.configuration.ServerConfiguration;
import webserver667.requests.HttpMethods;
import webserver667.requests.HttpRequest;
import webserver667.requests.RequestReader;

public class WebServer implements I667Server {
  private ServerSocket serverSocket = null;

  @Override
  public void close() throws Exception {
  }

  @Override
  public void start(ServerConfiguration configuration, MimeTypes mimeTypes) {
    try {
      serverSocket = new ServerSocket(configuration.getPort());
      while (true) {
        Socket clientSocket = serverSocket.accept();
        Thread requestThread = new Thread(() -> {
          try {
            RequestReader requestReader = new RequestReader(clientSocket.getInputStream());
            HttpRequest method = requestReader.getRequest();
            System.out.println(method.getHttpMethod());
            System.out.println(method.getUri());
            System.out.println(method.getVersion());
            System.out.println(method.getHeader("Host"));
            System.out.println(new String(method.getBody(), StandardCharsets.UTF_8));


          } catch (Exception e) {
            e.printStackTrace();
          } finally {
            try {
              clientSocket.close();
            } catch (IOException e) {
              e.printStackTrace();
            }

          }
        });
        requestThread.start();

      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void stop() {
  }
}
