package model;

import mock.MockRequest;
import org.junit.Test;
import util.HttpRequestUtils;
import util.RequestUtil;

import java.util.Map;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void queryParam으로부터_User인스턴스를_만들_수_있다() {
        Map<String, String> queryParam =
                RequestUtil.queryParamFromRequestedString(
                        MockRequest.requestedStrWithQueryString());

        User user = new User(
                queryParam.get("userId"),
                queryParam.get("password"),
                queryParam.get("name"),
                queryParam.get("email"));

        assertNotNull(user);
    }

}