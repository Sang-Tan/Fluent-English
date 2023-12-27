package com.fluentenglish.web.auth.admin;

import com.fluentenglish.web.auth.dto.LoginDto;
import com.fluentenglish.web.auth.dto.ResponseToken;
import com.fluentenglish.web.auth.exception.InvalidLoginCredentialsException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AdminAuthController {
    private final AdminLoginService adminLoginService;

    public AdminAuthController(AdminLoginService adminLoginService) {
        this.adminLoginService = adminLoginService;
    }

    @PostMapping("/admin/api/login")
    public ResponseEntity<Map<String, Object>> postLogin(@RequestBody @Valid LoginDto loginDto) {
        try {
            ResponseToken responseToken = adminLoginService.login(loginDto);
            return ResponseEntity.ok(Map.of("data", responseToken));
        } catch (InvalidLoginCredentialsException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid credentials"));
        }
    }
}
