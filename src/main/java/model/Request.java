package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.*;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final List<String> lines = new ArrayList<>();
    private final String method;
    private final Map<String, String> queryParam = new HashMap<>();
    private final String resource;
    private final String body;

    private Request(InputStream in) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in, DEFAULT_CHARSET));

            String line = null;

            System.out.println("\n=======================================");

            while (StringUtils.isPresent(line = reader.readLine())) {
                this.lines.add(line);
                System.out.println(line);
            }

            method = RequestUtil.findMethod(this.lines.get(0));
            queryParam.putAll(RequestUtil.queryParamFromRequestedString(this.lines.get(0)));
            resource = ResourceUtils.staticResourcePath(this.lines.get(0));
            body = IOUtils.readData(reader, contentLength());

            System.out.println("\n=======================================");
        } catch (IOException e) {
            log.error(e.getMessage());

            throw new RuntimeException();
        }
    }

    public static Request fromInputStream(InputStream in) {
        return new Request(in);
    }

    public String resource() {
        return this.resource;
    }

    public String postBody() {
        return this.body;
    }

    public int contentLength() {
        return lines.stream()
                .filter(x -> x.contains("Content-Length"))
                .findFirst()
                .map(x -> x.replace("Content-Length: ", ""))
                .map(Integer::parseInt)
                .orElseGet(() -> 0);
    }

}
