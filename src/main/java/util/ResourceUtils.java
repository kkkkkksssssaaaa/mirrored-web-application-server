package util;

import model.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.regex.Matcher;

public class ResourceUtils {

    private static final Logger log = LoggerFactory.getLogger(ResourceUtils.class);
    public static final String DEFAULT_PATH = "./webapp";

    public static byte[] staticResourceBytes(String line) {
        return bytes(staticResourcePath(line));
    }

    public static Map<String, String> queryParams(String line) {
        return HttpRequestUtils
                .parseQueryString(queryString(line));
    }

    public static String queryString(String requestedStr) {
        Matcher m = RequestValidator.matcher(requestedStr);

        if (!m.find()) {
            throw new IllegalArgumentException();
        }

        return m.group(3).replace("?", "");
    }

    private static File staticResource(String path) {
        return new File(DEFAULT_PATH + path);
    }

    public static String staticResourcePath(String line) {
        Matcher m = RequestValidator.matcher(line);

        if (!m.find()) {
            throw new IllegalArgumentException();
        }

        return m.group(2);
    }

    public static byte[] resource(String router) {
        return bytes(router);
    }

    private static byte[] bytes(String path) {
        try {
            File staticResource = staticResource(
                    Router.find(path));

            return Files
                    .readAllBytes(staticResource.toPath());
        } catch (IOException e) {
            log.error("{} is invalid resource.", path);

            return new byte[0];
        }
    }

}
