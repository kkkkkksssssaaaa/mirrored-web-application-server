package util;

public class StringUtils {

    /**
     * 대상 문자열이 null 이 아니거나, 비어있지 않은 문자열인 경우 true 반환
     * */
    public static boolean isPresent(String str) {
        if (null == str) {
            return false;
        }

        if (str.isEmpty()) {
            return false;
        }

        return true;
    }

}
