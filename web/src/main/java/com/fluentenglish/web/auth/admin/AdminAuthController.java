package com.fluentenglish.web.auth.admin;

import com.fluentenglish.web.auth.dto.LoginDto;
import com.fluentenglish.web.auth.dto.ResponseToken;
import com.fluentenglish.web.auth.exception.InvalidLoginCredentialsException;
import com.fluentenglish.web.common.exception.errorresponse.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminAuthController {
    private final AdminLoginService adminLoginService;

    public AdminAuthController(AdminLoginService adminLoginService) {
        this.adminLoginService = adminLoginService;
    }

    @PostMapping("/admin/api/login")
    public ResponseEntity<Object> postLogin(@RequestBody @Valid LoginDto loginDto) {
        try {
            ResponseToken responseToken = adminLoginService.login(loginDto);
            return ResponseEntity.ok(responseToken);
        } catch (InvalidLoginCredentialsException e) {
            return ResponseEntity.badRequest().body(ErrorResponse.builder()
                    .withMessage("Invalid email or password").build());
        }
    }
}
