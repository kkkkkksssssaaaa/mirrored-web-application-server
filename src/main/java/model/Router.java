package model;

import java.util.HashMap;
import java.util.Map;

public class Router {

    public static final String MAIN = "/index.html";
    public static final String USER_FORM = "/user/form.html";
    public static final String USER_LOGIN = "/user/login.html";

    private static final Map<String, String> pages = new HashMap<>();

    static {
        pages.put("/index.html", MAIN);
        pages.put("/user/create", USER_FORM);
        pages.put("/user/form.html", USER_FORM);
        pages.put("/user/login.html", USER_LOGIN);
    }

}
