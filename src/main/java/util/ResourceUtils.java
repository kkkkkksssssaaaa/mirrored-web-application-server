package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.regex.Matcher;

public class ResourceUtils {

    private static final Logger log = LoggerFactory.getLogger(ResourceUtils.class);

    public static byte[] staticResourceBytes(String line) {
        if (ResourceValidator.isContainQueryParameter(line)) {
            // TODO 정적 리소스(.html) 유지하여 응답해야함
            return new byte[0];
        }

        if (!ResourceValidator.isPresent(staticResource(line))) {
            return new byte[0];
        }

        try {
            return Files
                    .readAllBytes(resource(
                            staticResource(line)).toPath());
        } catch (IOException e) {
            log.error("{} is invalid resource.", line);

            return new byte[0];
        }
    }

    public static Map<String, String> queryParams(String line) {
        return HttpRequestUtils.parseQueryString(
                queryString(line));
    }

    private static File resource(String path) {
        return new File(ResourceValidator.DEFAULT_PATH + path);
    }

    public static String staticResource(String requestedStr) {
        Matcher m = ResourceValidator.matcher(requestedStr);

        if (!m.find()) {
            throw new IllegalArgumentException();
        }

        return m.group(2);
    }

    public static String queryString(String requestedStr) {
        Matcher m = ResourceValidator.matcher(requestedStr);

        if (!m.find()) {
            throw new IllegalArgumentException();
        }

        return m.group(3).replace("?", "");
    }
}
