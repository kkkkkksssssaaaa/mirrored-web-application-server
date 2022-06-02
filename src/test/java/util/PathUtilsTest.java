package util;

import mock.MockRequest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @see MockRequest
 * */
public class PathUtilsTest {

    @Test
    public void 요청_문자열_정규식_검사() {
        assertTrue(PathUtils.isRequestPatternMatched(MockRequest.requestedStr()));
    }

    @Test
    public void 요청_문자열에서_queryString을_추출할_수_있다() {
        String queryString = PathUtils.queryString(MockRequest.requestedStr());

        assertEquals(
                queryString,
                "userId=haesam&password=password1!&name=왕해삼&email=kkkkkksssssaaaa.dev@gmail.com");
    }

}