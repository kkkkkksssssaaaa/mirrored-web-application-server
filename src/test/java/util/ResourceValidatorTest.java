package util;

import mock.MockRequest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @see MockRequest
 * */
public class ResourceValidatorTest {

    @Test
    public void 요청_문자열_정규식_검사() {
        assertTrue(ResourceValidator.isRequestPatternMatched(MockRequest.requestedStrWithQueryString()));
        assertTrue(ResourceValidator.isRequestPatternMatched(MockRequest.requestedStr()));
    }

    @Test
    public void 요청_문자열에서_queryString을_추출할_수_있다() {
        String queryString = ResourceValidator.queryString(MockRequest.requestedStrWithQueryString());

        assertEquals(
                queryString,
                "userId=haesam&password=password1!&name=왕해삼&email=kkkkkksssssaaaa.dev@gmail.com");
    }

    @Test
    public void 요청_문자열에서_resource를_추출할_수_있다() {
        String queryString = ResourceValidator.resourcePath(MockRequest.requestedStr());

        assertEquals(
                queryString,
                "/index.html");
    }

    @Test
    public void 요청_문자열에서_queryParam이_존재하는지_구분할_수_있다() {
        assertTrue(ResourceValidator.isContainQueryParameter(MockRequest.requestedStrWithQueryString()));
        assertFalse(ResourceValidator.isContainQueryParameter(MockRequest.requestedStr()));
    }

}