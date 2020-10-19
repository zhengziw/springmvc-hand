package com.mvc.controller;

import com.mvc.annotation.AutoWired;
import com.mvc.annotation.Controller;
import com.mvc.annotation.RequestMapping;
import com.mvc.annotation.RequestParam;
import com.mvc.pojo.User;
import com.mvc.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
@Controller
@RequestMapping("/user")
public class UserController {
    @AutoWired("userServiceImpl")
    private UserService userService;
    @RequestMapping("/query")
    public  void findUsers(HttpServletRequest request, HttpServletResponse response,@RequestParam("name")String name) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        List<User> users=userService.findUsers(name);
        PrintWriter printWriter=response.getWriter();
        printWriter.print(name);
    }
}
