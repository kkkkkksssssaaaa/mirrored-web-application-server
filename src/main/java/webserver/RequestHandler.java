package webserver;

import java.io.*;
import java.net.Socket;

import db.DataBase;
import model.Request;
import model.Response;
import model.Router;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.*;

/**
 * 2. WebServer 가 클라이언트의 요청을 받으면 새로운 스레드를 실행
 * WebServer 로부터 넘겨받은 소켓을 통해 클라이언트의 요청을 처리
 *
 * run() 의 InputStream 은 클라이언트의 요청 데이터를 받아아오는 스트림이고,
 * OutputStream 은 클라이언트에게 응답할 데이터에 대한 스트림임
 * */
public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug(
                "\nNew Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {

            byte[] body = null;
            User user = null;

            Request req = Request.fromInputStream(in);

            Router.route(req, out);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
