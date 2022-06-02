package util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceValidator {

    public static final Pattern REQUEST_PATTERN =
            Pattern.compile("(GET|POST|PUT|DELETE)\\s+([^?\\s]+)((?:[?&][^&\\s]+)*)\\s+(HTTP/.*)");
    public static final String DEFAULT_PATH = "./webapp";

    public static boolean isContainQueryParameter(String requestedStr) {
        Matcher m = REQUEST_PATTERN.matcher(requestedStr);

        if (!m.find()) {
            throw new IllegalArgumentException();
        }

        return !m.group(3).isEmpty();
    }

    public static boolean isRequestPatternMatched(String requestedStr) {
        return REQUEST_PATTERN.matcher(requestedStr).find();
    }

    public static Matcher matcher(String requestedStr) {
        return REQUEST_PATTERN.matcher(requestedStr);
    }

    public static boolean isPresent(String path) {
        return new File(DEFAULT_PATH + path).exists();
    }

}
