package webserver667.responses;

public enum HttpResponseCode {
  OK(200, "OK"),
  CREATED(201, "Created"),
  NO_CONTENT(204, "No Content"),
  NOT_MODIFIED(304, "Not Modified"),
  BAD_REQUEST(400, "Bad Request"),
  UNAUTHORIZED(401, "Unauthorized"),
  FORBIDDEN(403, "Forbidden"),
  NOT_FOUND(404, "Not Found"),
  METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
  INTERNAL_SERVER_ERROR(500, "Internal Server Error");

  private final int code;
  private final String reasonPhrase;

  HttpResponseCode(int code, String reasonPhrase) {
    this.code = code;
    this.reasonPhrase = reasonPhrase;
  }

  public String getReasonPhrase() {
    return this.reasonPhrase;
  }

  public int getCode() {
    return this.code;
  }
}
