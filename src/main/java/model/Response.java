package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {

    private static final Logger log = LoggerFactory.getLogger(Response.class);
    private final int code;
    private final byte[] body;
    private final OutputStream out;
    private final DataOutputStream dos;
    private final List<String> headers = new ArrayList<>();

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

        headers.add(
                String.format("HTTP/1.1 %d %s \r\n", status().getKey(), status().getValue()));
        headers.add(
                "Content-Type: text/html;charset=utf-8\r\n");
        headers.add(
                String.format("Content-Length: %d\r\n", this.body.length));
    }

    public static Response create(int code,
                                  byte[] body,
                                  OutputStream out) {
        return new Response(code, body, out);
    }

    public Response setCookie(String key, String value) {
        headers.add(String.format("Set-Cookie: %s=%s\r\n", key, value));

        return this;
    }

    public Response setLocation(String location) {
        headers.add(String.format("Location: %s\r\n", location));

        return this;
    }

    public void flush() {
        headers.add("\r\n");

        try {
            writeHeader();

            dos.write(this.body, 0, this.body.length);
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
        headers.forEach(this::writeByte);
    }

    private void writeByte(String header) {
        try {
            dos.writeBytes(header);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
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
