package controller;

import db.DataBase;
import model.Request;
import model.Response;
import model.Router;
import model.User;
import util.ResourceUtils;

import java.io.OutputStream;

public class UserController {

    private final Request req;
    private final OutputStream out;

    public UserController(Request req,
                          OutputStream out) {
        this.req = req;
        this.out = out;
    }

    public void create() {
        User user = User.fromQueryString(req.postBody());
        DataBase.addUser(user);

        Response.create(
                302, ResourceUtils.getBytes(Router.PAGE_MAIN), out)
                .setLocation(Router.PAGE_MAIN)
                .flush();
    }

    public void login() {
        User user = User.fromQueryString(req.postBody());

        if (DataBase.findAll().contains(user)) {
            Response.create(
                            302, ResourceUtils.getBytes(Router.PAGE_MAIN), out)
                    .setCookie("logined", Boolean.TRUE.toString())
                    .setLocation(Router.PAGE_MAIN)
                    .flush();
        } else {
            Response.create(
                            302, ResourceUtils.getBytes(Router.PAGE_USER_LOGIN_FAILED), out)
                    .setCookie("logined", Boolean.FALSE.toString())
                    .setLocation(Router.PAGE_USER_LOGIN_FAILED)
                    .flush();
        }
    }

    public void list() {
        if (req.cookies().isEmpty()
                || req.cookies().get("logined").isEmpty()
                || req.cookies().get("logined").equals(Boolean.FALSE.toString())) {

            Response.create(
                            302, ResourceUtils.getBytes(Router.PAGE_USER_LOGIN), out)
                    .setLocation(Router.PAGE_USER_LOGIN)
                    .flush();
        } else {
            StringBuilder sb = new StringBuilder();

            DataBase.findAll()
                    .forEach(x -> {
                        sb.append(x);
                        sb.append("\n");
                    });

            Response.create(
                            200, ResourceUtils.getBytes(sb), out)
                    .flush();
        }
    }

}
