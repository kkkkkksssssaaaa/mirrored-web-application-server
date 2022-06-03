package model;

import java.util.HashMap;
import java.util.Map;

public class Router {

    public static final String PAGE_MAIN = "/index.html";
    public static final String PAGE_USER_FORM = "/user/form.html";
    public static final String PAGE_USER_LOGIN = "/user/login.html";
    public static final String PAGE_USER_LOGIN_FAILED = "/user/login_failed.html";
    public static final String USER_CREATE = "/user/create";

    private static final Map<String, String> pages = new HashMap<>();

    static {
        pages.put(PAGE_MAIN, PAGE_MAIN);
        pages.put(USER_CREATE, PAGE_USER_FORM);
        pages.put(PAGE_USER_FORM, PAGE_USER_FORM);
        pages.put(PAGE_USER_LOGIN, PAGE_USER_LOGIN);
        pages.put(PAGE_USER_LOGIN_FAILED, PAGE_USER_LOGIN_FAILED);
    }

    public static String find(String key) {
        if (!pages.containsKey(key)) {
            return key;
        }

        return pages.get(key);
    }

}
