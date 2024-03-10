package tests.webserver667.responses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import startup.configuration.MimeTypes;
import webserver667.requests.HttpMethods;
import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.Resource;

public class ResourceTest {
  private MimeTypes getMimeTypes() {
    return new MimeTypes("html index/html" + System.lineSeparator());
  }

  private static Path createDocumentRoot(Path path) throws IOException {
    Path documentRoot = Files.createTempDirectory("documentRoot");

    Files.createDirectories(Paths.get(
        documentRoot.toString(),
        path.toString()));

    return documentRoot;
  }

  private static HttpRequest testRequest = new HttpRequest();
  static {
    testRequest.setUri("/doesnt/matter/index.html");
  }

  @Test
  public void testExistsForExistingFile() throws IOException {
    Path resourcePath = Paths.get("doesnt", "matter");
    Path documentRoot = createDocumentRoot(resourcePath);

    Path tempFile = Files.createTempFile(
        Paths.get(documentRoot.toString(), resourcePath.toString()), "index", ".html");

    String name = tempFile.toFile().getName();
    testRequest.setUri(Paths.get("doesnt", "matter", name).toString());

    IResource resource = new Resource(
        testRequest,
        documentRoot.toString(),
        getMimeTypes());

    assertTrue(resource.exists());
  }

  @Test
  public void testExistsForNoFile() throws IOException {
    Path resourcePath = Paths.get("doesnt", "matter");
    Path documentRoot = createDocumentRoot(resourcePath);

    IResource resource = new Resource(testRequest, documentRoot.toString(), getMimeTypes());

    assertFalse(resource.exists());
  }

  @Test
  public void testGetPath() throws IOException {
    Path resourcePath = Paths.get("doesnt", "matter", "index.html");
    Path documentRoot = createDocumentRoot(resourcePath);

    IResource resource = new Resource(testRequest, documentRoot.toString(), getMimeTypes());
    Path expectedPath = Paths.get(documentRoot.toString(), resourcePath.toString());

    assertEquals(expectedPath, resource.getPath());
  }

  @Test
  public void testIsProtectedWhenDirectoryContainsPasswordsFile() throws IOException {
    Path resourcePath = Paths.get("doesnt", "matter");
    Path documentRoot = createDocumentRoot(resourcePath);

    IResource resource = new Resource(testRequest, documentRoot.toString(), getMimeTypes());

    File passwords = new File(
        Paths.get(documentRoot.toString(), resourcePath.toString(), ".passwords").toAbsolutePath().toString());

    FileWriter writer = new FileWriter(passwords);
    writer.write("somecontent");
    writer.flush();
    writer.close();

    assertTrue(resource.isProtected());
  }

  @Test
  public void testIsProtectedWhenDirectoryDoesNotContainsPasswordsFile() throws IOException {
    Path resourcePath = Paths.get("doesnt", "matter");
    Path documentRoot = createDocumentRoot(resourcePath);

    IResource resource = new Resource(testRequest, documentRoot.toString(), getMimeTypes());

    assertFalse(resource.isProtected());
  }

  @Test
  public void testIsScriptWhenInScriptDirectory() throws IOException {
    Path resourcePath = Paths.get("doesnt", "scripts");
    Path documentRoot = createDocumentRoot(resourcePath);

    HttpRequest request = new HttpRequest();
    request.setUri(testRequest.getUri());
    request.setHttpMethod(HttpMethods.POST);

    IResource resource = new Resource(request, documentRoot.toString(), getMimeTypes());

    assertTrue(resource.isScript());
  }

  @Test
  public void testIsScriptWhenNotInScriptDirectory() throws IOException {
    Path resourcePath = Paths.get("doesnt", "matter");
    Path documentRoot = createDocumentRoot(resourcePath);

    IResource resource = new Resource(testRequest, documentRoot.toString(), getMimeTypes());

    assertFalse(resource.isScript());
  }
}
