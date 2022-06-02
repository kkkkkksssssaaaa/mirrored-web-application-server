package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class ResourceUtils {

    private static final Logger log = LoggerFactory.getLogger(ResourceUtils.class);
    private static final String DEFAULT_PATH = "./webapp";

    public static byte[] staticResourceBytes(String line) {
        if (ResourceValidator.isContainQueryParameter(line)) {
            // TODO 정적 리소스(.html) 유지하여 응답해야함
            return new byte[0];
        }

        if (!isPresent(ResourceValidator.resourcePath(line))) {
            return new byte[0];
        }

        try {
            return Files
                    .readAllBytes(resource(
                            ResourceValidator.resourcePath(line)).toPath());
        } catch (IOException e) {
            log.error("{} is invalid resource.", line);

            return new byte[0];
        }
    }

    public static Map<String, String> queryParams(String line) {
        return HttpRequestUtils.parseQueryString(
                ResourceValidator.queryString(line));
    }

    private static File resource(String path) {
        return new File(DEFAULT_PATH + path);
    }

    private static boolean isPresent(String path) {
        return new File(DEFAULT_PATH + path).exists();
    }

}
