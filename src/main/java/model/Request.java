package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;
import util.StringUtils;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Request {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final List<String> lines = new ArrayList<>();

    private Request(InputStream in) {
        readStream(in);
    }

    public static Request fromInputStream(InputStream in) {
        return new Request(in);
    }

    public String resource() {
        return this.lines.get(0);
    }

    public int contentLength() {
        return lines.stream()
                .filter(x -> x.contains("Content-Length"))
                .findFirst()
                .map(x -> x.replace("Content-Length: ", ""))
                .map(Integer::parseInt)
                .orElseGet(() -> 0);
    }

    private void readStream(InputStream in) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in, DEFAULT_CHARSET));

            String line = null;

            System.out.println("\n=======================================");

            while (StringUtils.isPresent(line = reader.readLine())) {
                this.lines.add(line);
                System.out.println(line);
            }

            System.out.println("\n=======================================");
        } catch (IOException e) {
            log.error(e.getMessage());

            throw new RuntimeException();
        }
    }

}
