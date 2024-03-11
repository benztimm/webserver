package webserver667;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import startup.configuration.MimeTypes;
import startup.configuration.ServerConfiguration;

import webserver667.requests.HttpRequest;
import webserver667.requests.RequestReader;
import webserver667.responses.Resource;
import webserver667.responses.writers.ResponseWriter;
import webserver667.responses.writers.ResponseWriterFactory;

public class WebServer implements I667Server {
  private ServerSocket serverSocket = null;
  private volatile boolean isRunning = false;


  @Override
  public void close() throws Exception {
    stop();
  }

  @Override
  public void start(ServerConfiguration configuration, MimeTypes mimeTypes) {
    isRunning = true;
    try {
      serverSocket = new ServerSocket(configuration.getPort());
      while (isRunning) {
        Socket clientSocket = serverSocket.accept();
        Thread requestThread = new Thread(() -> {
          try {
            RequestReader requestReader = new RequestReader(clientSocket.getInputStream());

            HttpRequest request = requestReader.getRequest();
            OutputStream out = clientSocket.getOutputStream();
            Resource resource =new Resource(request, configuration.getDocumentRoot().toString(), mimeTypes);
            ResponseWriter writer = ResponseWriterFactory.create(out, resource, request);
            writer.write();
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
    isRunning = false; 
    try {
      if (serverSocket != null && !serverSocket.isClosed()) {
        serverSocket.close(); 
      }
    } catch (IOException e) {
      e.printStackTrace(); 
    }
  }
}
