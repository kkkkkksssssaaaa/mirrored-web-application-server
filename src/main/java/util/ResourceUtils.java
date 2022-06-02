package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ResourceUtils {

    private static final Logger log = LoggerFactory.getLogger(ResourceUtils.class);
    private static final String DEFAULT_PATH = "./webapp";

    public static byte[] resourceBytes(String path) {
        if (!isPresent(path)) {
            return null;
        }

        try {
            return Files.readAllBytes(resource(path).toPath());
        } catch (IOException e) {
            log.error("{} is invalid resource.", path);

            throw new RuntimeException();
        }
    }

    private static File resource(String path) {
        return new File(DEFAULT_PATH + path);
    }

    private static boolean isPresent(String path) {
        return new File(DEFAULT_PATH + path).exists();
    }

}
