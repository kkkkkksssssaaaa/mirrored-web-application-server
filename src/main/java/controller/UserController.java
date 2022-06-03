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
                .flush(Router.PAGE_MAIN);
    }

}
