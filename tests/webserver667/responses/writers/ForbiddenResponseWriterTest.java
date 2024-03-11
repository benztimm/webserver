package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import tests.helpers.responses.CompareTestOutputAndExpectedOutput;
import tests.helpers.responses.TestOutputStream;
import tests.helpers.responses.TestResource;
import webserver667.exceptions.responses.ServerErrorException;
import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.writers.ForbiddenResponseWriter;
import webserver667.responses.writers.ResponseWriter;

public class ForbiddenResponseWriterTest {

  @Test
  public void testWrite() throws IOException, ServerErrorException {
    IResource testResource = new TestResource();
    TestOutputStream out = new TestOutputStream();

    HttpRequest request = new HttpRequest();
    request.setVersion("HTTP/1.1");

    ResponseWriter writer = new ForbiddenResponseWriter(out, testResource, request);
    writer.write();

    CompareTestOutputAndExpectedOutput comparator = new CompareTestOutputAndExpectedOutput(out);

    assertTrue(comparator.headContains("HTTP/1.1 403 Forbidden\r\n".getBytes()));
  }

}
