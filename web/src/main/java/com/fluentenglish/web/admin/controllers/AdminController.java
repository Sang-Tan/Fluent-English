package com.fluentenglish.web.admin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AdminController {
    @ResponseBody
    @GetMapping("/admin")
    public String getAdminPage() {

        return "Admin Page<br>" + "<a href=\"/admin/logout\">Logout</a>";
    }

    @GetMapping("/admin/login")
    public String getLoginPage() {
        return "login";
    }
}
