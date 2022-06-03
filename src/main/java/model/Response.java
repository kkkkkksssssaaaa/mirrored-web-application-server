package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Response {

    private static final Logger log = LoggerFactory.getLogger(Response.class);
    private final int code;
    private final byte[] body;
    private final OutputStream out;
    private final DataOutputStream dos;

    private static final Map<Integer, String> acceptCodes = new HashMap<>();

    static {
        acceptCodes.put(200, "OK");
        acceptCodes.put(302, "Found");
    }

    private Response(int code,
                     byte[] body,
                     OutputStream out) {
        validateStatusCode(code);

        this.code = code;
        this.body = body;
        this.out = out;
        this.dos = new DataOutputStream(out);
    }

    public static Response create(int code,
                                  byte[] body,
                                  OutputStream out) {
        return new Response(code, body, out);
    }

    public void flush() {
        try {
            writeHeader();
            dos.writeBytes("\r\n");
            writeBody();
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void flush(String location) {
        try {
            writeHeader();
            dos.writeBytes("Location: " + location);
            dos.writeBytes("\r\n");
            writeBody();
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void validateStatusCode(int code) {
        if (!acceptCodes.containsKey(code)) {
            throw new IllegalArgumentException("not found status");
        }
    }

    private void writeHeader() {
        try {
            dos.writeBytes(head());
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + this.body.length + "\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void writeBody() {
        try {
            dos.write(this.body, 0, this.body.length);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String head() {
        return String.format("HTTP/1.1 %d %s \r\n", status().getKey(), status().getValue());
    }

    private Map.Entry<Integer, String> status() {
        return acceptCodes
                .entrySet()
                .stream()
                .filter(x -> x.getKey().equals(this.code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("not found status"));
    }

}
