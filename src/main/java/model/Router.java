package model;

import util.ResourceUtils;

import java.util.HashMap;
import java.util.Map;

public class Router {

    public static final String MAIN = "/index.html";
    public static final String USER_CREATE = "/user/create";
    public static final String USER_FORM = "/user/form.html";
    public static final String USER_LOGIN = "/user/login.html";
    public static final String USER_LOGIN_FAILED = "/user/login_failed.html";

    private static final Map<String, String> pages = new HashMap<>();

    static {
        pages.put(MAIN, MAIN);
        pages.put(USER_CREATE, USER_FORM);
        pages.put(USER_FORM, USER_FORM);
        pages.put(USER_LOGIN, USER_LOGIN);
        pages.put(USER_LOGIN_FAILED, USER_LOGIN_FAILED);
    }

    public static String find(String key) {
        if (!pages.containsKey(key)) {
            return key;
        }

        return pages.get(key);
    }

}
