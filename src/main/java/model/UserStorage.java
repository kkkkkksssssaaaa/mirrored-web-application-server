package model;

import java.util.ArrayList;
import java.util.List;

public class UserStorage {

    private static final List<User> USERS = new ArrayList<>();

    public static void add(User user) {
        USERS.add(user);
    }

}
