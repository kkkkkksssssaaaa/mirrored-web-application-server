package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.ResourceUtils;
import util.ResourceValidator;
import util.StringUtils;

/**
 * 2. WebServer 가 클라이언트의 요청을 받으면 새로운 스레드를 실행
 * WebServer 로부터 넘겨받은 소켓을 통해 클라이언트의 요청을 처리
 *
 * run() 의 InputStream 은 클라이언트의 요청 데이터를 받아아오는 스트림이고,
 * OutputStream 은 클라이언트에게 응답할 데이터에 대한 스트림임
 * */
public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("\nNew Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in, DEFAULT_CHARSET));

            String line = null;
            byte[] body = null;
            User user = null;

            while (StringUtils.isPresent(line = reader.readLine())) {
                if (ResourceValidator.isRequestPatternMatched(line)) {
                    body = ResourceUtils.staticResourceBytes(line);
                    user = createUser(line);
                }

                System.out.println(line);
            }

            response200Header(dos, body.length);
            responseBody(dos, body);

            System.out.println("\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private User createUser(String line) {
        Map<String, String> queryParam =
                HttpRequestUtils.parseQueryString(
                        ResourceValidator.queryString(line));

        return new User(
                queryParam.get("userId"),
                queryParam.get("password"),
                queryParam.get("name"),
                queryParam.get("email"));
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
