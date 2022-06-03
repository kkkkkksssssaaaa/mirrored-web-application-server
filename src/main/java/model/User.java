package model;

import com.google.common.base.Strings;
import util.HttpRequestUtils;

import java.util.Map;
import java.util.Objects;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public static User fromQueryParam(Map<String, String> queryParam) {
        return new User(
                queryParam.get("userId"),
                queryParam.get("password"),
                queryParam.get("name"),
                queryParam.get("email"));
    }

    public static User fromQueryString(String queryString) {
        return fromQueryParam(HttpRequestUtils.parseQueryString(queryString));
    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(this.userId)
                && Strings.isNullOrEmpty(this.password)
                && Strings.isNullOrEmpty(this.name)
                && Strings.isNullOrEmpty(this.email);
    }

    @Override
    public boolean equals(Object o) {
        if (!(this.getClass().equals(o.getClass()))) {
            return false;
        }

        return this.getUserId().equals(((User) o).getUserId())
                && this.getPassword().equals(((User) o).getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId(), this.getPassword());
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }

}
