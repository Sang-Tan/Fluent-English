package com.fluentenglish.web.auth.admin;

import com.fluentenglish.web.auth.Role;
import com.fluentenglish.web.auth.dto.LoginDto;
import com.fluentenglish.web.auth.dto.ResponseToken;
import com.fluentenglish.web.auth.exception.InvalidLoginCredentialsException;
import com.fluentenglish.web.auth.token.jwt.JWTProcessor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminLoginService {
    private final JWTProcessor jwtProcessor;

    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    public AdminLoginService(JWTProcessor jwtProcessor, AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.jwtProcessor = jwtProcessor;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseToken login(LoginDto loginDto) {
        return login(loginDto.getUsername(), loginDto.getPassword());
    }

    private ResponseToken login(String email, String password) {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(InvalidLoginCredentialsException::new);

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new InvalidLoginCredentialsException();
        }

        String token = jwtProcessor.getBuilder()
                .withSubject(admin.getEmail())
                .withScopes(List.of("ROLE_"+ Role.ADMIN.name()))
                .build();
        ResponseToken responseToken = new ResponseToken();
        responseToken.setAccessToken(token);

        return responseToken;
    }
}
