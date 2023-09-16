package com.fluentenglish.web.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class UserController {
    @GetMapping("/user/login")
    public String getUserPage() {
        return "login_user";
    }
    @ResponseBody
    @GetMapping("/user")
    public String getUser() {
        return "User Page<br>" + "<a href=\"/user/logout\">Logout</a>";
    }
}
