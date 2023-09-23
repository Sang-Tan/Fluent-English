package com.fluentenglish.web.auth.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminAuthController {
    @ResponseBody
    @GetMapping("/admin")
    public String getAdminPage() {
        return "Admin Page<br>" + "<a href=\"/admin/logout\">Logout</a>";
    }
    @GetMapping("/admin/login")
    public String getLoginPage() {
        return "auth/login_admin";
    }
}
