package mock;

/**
 * 테스트에 공통으로 사용되는 MockRequest 에 대한 객체입니다.
 * */
public class MockRequest {

    private static final String userId = "haesam";
    private static final String password = "password1!";
    private static final String name = "왕해삼";
    private static final String email = "kkkkkksssssaaaa.dev@gmail.com";

    private static final String requestedStr =
            String.format(
                    "GET " +
                            "/user/create?" +
                            "userId=%s" +
                            "&password=%s" +
                            "&name=%s" +
                            "&email=%s " +
                            "HTTP/1.1",
                    userId, password, name, email);

    public static String requestedStr() {
        return requestedStr;
    }

}
