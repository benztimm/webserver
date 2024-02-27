package tests.helpers.requests;

import java.io.IOException;
import java.io.InputStream;

/**
 * The TestInputStream object is used to mock an InputStream
 * for the purpose of providing test input streams with user
 * defined content to the object under test.
 */
public class TestInputStream extends InputStream {

  private int index = 0;
  private byte[] testContent;

  /**
   * @param testContent The content that will be read from the stream
   */
  public TestInputStream(byte[] testContent) {
    this.testContent = testContent;
  }

  @Override
  public int read() throws IOException {
    if (index == testContent.length) {
      return -1;
    } else {
      return testContent[index++];
    }
  }

  @Override
  public int read(byte[] destination) {
    int result = destination.length;

    for (int offset = 0; offset < destination.length; offset++) {
      destination[offset] = testContent[this.index++];
    }

    return result;
  }
}
