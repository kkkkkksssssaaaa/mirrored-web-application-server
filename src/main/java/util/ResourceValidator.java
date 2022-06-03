package util;

import db.DataBase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceValidator {

    public static final Pattern REQUEST_PATTERN =
            Pattern.compile("(GET|POST|PUT|DELETE)\\s+([^?\\s]+)((?:[?&][^&\\s]+)*)\\s+(HTTP/.*)");
    public static final String DEFAULT_PATH = "./webapp";
    public static final String MAIN = "/index.html";

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
        return DataBase.STATIC_RESOURCES.contains(path);
    }

    public static String findStaticResource(String path) {
        if ("/index.html".equals(path)) {
            return "/index.html";
        }

        if ("/user/create".equals(path)
                || "/user/form.html".equals(path)) {
            return "/user/form.html";
        }

        if ("/user/login.html".equals(path)) {
            return "/user/login.html";
        }

        return "";
    }

}
