package tests.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

import startup.configuration.ServerConfiguration;
import webserver667.WebServer;
import webserver667.exceptions.configuration.ConfigurationHelpRequestedException;
import webserver667.exceptions.configuration.InvalidServerConfigurationException;
import gui.ava.html.image.generator.HtmlImageGenerator;

public class ManualTest {

    private class ServerThread extends Thread {
        private WebServer server;
        private ServerConfiguration config;
        private final String[] testParams = new String[] {
                "-p", "9876", "-r",
                Paths.get("public_html").toAbsolutePath().toString(),
                "-m", String.join(System.lineSeparator(),
                        List.of(
                                "text/html html",
                                "img/jpeg jpg",
                                "text/javascript js",
                                "text/css css"))
        };

        public ServerThread() throws InvalidServerConfigurationException, ConfigurationHelpRequestedException,
                IOException, InterruptedException {
            this.server = new WebServer();
            this.config = ServerConfiguration.getDefault(this.testParams);

            unpackTestSite();
        }

        @Override
        public void run() {
            this.server.start(config, config.getMimeFileContent());
        }

        public void close() {
            this.server.stop();
        }

        private void unpackTestSite() throws IOException, InterruptedException {
            String tarPath = Paths.get("tests", "integration", "public_html.tar").toAbsolutePath().toString();

            Process process = Runtime.getRuntime().exec(new String[] {
                    "tar", "xvf", tarPath });
            process.waitFor();
        }
    }

    private ServerThread serverThread;

    public void setUp() throws IOException, InterruptedException, InvalidServerConfigurationException,
            ConfigurationHelpRequestedException {
        serverThread = new ServerThread();
        serverThread.start();
    }

    public void tearDown() {
        serverThread.close();
    }

    @Test
    public void testFullWebsite() {
        try {
            Path path = Paths.get(".", "manual-test.png");
            Files.createDirectories(path.getParent());
            File f = path.toFile();

            URL url = new URI("http://localhost:9876/index.html").toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(60000);
            connection.setReadTimeout(60000);

            BufferedReader reader;

            if (100 <= connection.getResponseCode() && connection.getResponseCode() <= 399) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String content = buffer.toString();
            System.out.println(content);

            HtmlImageGenerator generator = new HtmlImageGenerator();
            generator.loadHtml(content);
            generator.saveAsImage(f.getAbsolutePath());

            assertTrue(f.exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
