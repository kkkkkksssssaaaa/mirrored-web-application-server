package util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class RequestUtil {

    public static String findMethod(String requestedStr) {
        Matcher m = RequestValidator.matcher(requestedStr);

        if (!m.find()) {
            throw new IllegalArgumentException();
        }

        return m.group(1);
    }

    public static String queryString(String requestedStr) {
        Matcher m = RequestValidator.matcher(requestedStr);

        if (!m.find()) {
            throw new IllegalArgumentException();
        }

        return m.group(3).replace("?", "");
    }

    public static Map<String, String> queryParamFromQueryString(String queryString) {
        if (!RequestValidator.isContainQueryParameter(queryString)) {
            return new HashMap<>();
        }

        return HttpRequestUtils.parseQueryString(queryString);
    }

    public static Map<String, String> queryParamFromRequestedString(String requestedStr) {
        return HttpRequestUtils.parseQueryString(queryString(requestedStr));
    }

}
