package webserver;

import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 1. 웹 서버를 시작하고 클라이언트의 요청을 받기 위해 대기
 * 요청이 있을 경우 해당 요청을 RequestHandler 클래스에 위임
 *
 * 클라이언트의 요청을 받기 위해 서버 소켓을 생성함
 *
 * RequestHandler 인스턴스를 생성하여 해당 요청을 처리하기 위한 스레드를 실행하는 클래스임
 *
 * @see RequestHandler
 * */
public class WebServer {
    private static final Logger log = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            log.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                RequestHandler requestHandler = new RequestHandler(connection);
                requestHandler.start();
            }
        }
    }
}
