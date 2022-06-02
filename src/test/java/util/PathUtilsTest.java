package util;

import org.junit.Test;

import static org.junit.Assert.*;

public class PathUtilsTest {

    private final String userId = "haesam";
    private final String password = "password1!";
    private final String name = "왕해삼";
    private final String email = "kkkkkksssssaaaa.dev@gmail.com";

    private final String requestedStr =
            String.format(
                    "GET " +
                            "/user/create?" +
                            "userId=%s" +
                            "&password=%s" +
                            "&name=%s" +
                            "&email=%s " +
                            "HTTP/1.1",
                    userId, password, name, email);

    @Test
    public void 요청_문자열_정규식_검사() {
        assertTrue(PathUtils.isRequestPatternMatched(requestedStr));
    }

    @Test
    public void 요청_문자열에서_queryString을_추출할_수_있다() {
        String queryString = PathUtils.queryString(requestedStr);

        assertEquals(
                queryString,
                "userId=haesam&password=password1!&name=왕해삼&email=kkkkkksssssaaaa.dev@gmail.com");
    }

}