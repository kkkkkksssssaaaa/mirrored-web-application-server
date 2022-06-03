package db;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import model.User;

public class DataBase {
    public static final List<String> STATIC_RESOURCES =
            Arrays.asList(
                    "/index.html",
                    "/user/create",
                    "/user/login.html",
                    "/user/form.html");
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
