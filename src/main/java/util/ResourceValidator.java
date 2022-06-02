package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceValidator {

    public static final Pattern REQUEST_PATTERN =
            Pattern.compile("(GET|POST|PUT|DELETE)\\s+([^?\\s]+)((?:[?&][^&\\s]+)*)\\s+(HTTP/.*)");
    public static final String DEFAULT_PATH = "./webapp";
    private static final List<String> STATIC_RESOURCES =
            Arrays.asList(
                    "/index.html",
                    "/user/create",
                    "/user/form.html");

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
        return STATIC_RESOURCES.contains(path);
    }

    public static String findStaticResource(String path) {
        if ("/index.html".equals(path)) {
            return "/index.html";
        }

        if ("/user/create".equals(path)
                || "/user/form.html".equals(path)) {
            return "/user/form.html";
        }

        return "";
    }

}
