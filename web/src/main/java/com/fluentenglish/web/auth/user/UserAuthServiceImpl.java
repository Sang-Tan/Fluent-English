package com.fluentenglish.web.auth.user;

import com.fluentenglish.web.auth.Role;
import com.fluentenglish.web.auth.dto.LoginDto;
import com.fluentenglish.web.auth.token.jwt.VerifiedJWT;
import com.fluentenglish.web.auth.user.dto.*;
import com.fluentenglish.web.auth.dto.ResponseToken;
import com.fluentenglish.web.auth.exception.InvalidLoginCredentialsException;
import com.fluentenglish.web.auth.token.jwt.JWTProcessor;
import com.fluentenglish.web.auth.user.service.UserAuthEmailService;
import com.fluentenglish.web.user.User;
import com.fluentenglish.web.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAuthServiceImpl implements UserAuthService {
    private final UserRepository userRepository;

    private final JWTProcessor jwtProcessor;

    private final PasswordEncoder passwordEncoder;

    private final UserAuthEmailService userAuthEmailService;

    private final long verificationDuration;

    public UserAuthServiceImpl(@Value("${auth.jwt.verification-duration}") long verificationDuration,
                               UserRepository userRepository,
                               JWTProcessor jwtProcessor,
                               PasswordEncoder passwordEncoder,
                               UserAuthEmailService userAuthEmailService) {
        this.verificationDuration = verificationDuration;
        this.userRepository = userRepository;
        this.jwtProcessor = jwtProcessor;
        this.passwordEncoder = passwordEncoder;
        this.userAuthEmailService = userAuthEmailService;
    }


    @Override
    public ResponseToken login(LoginDto loginDto) {
        String email = loginDto.getUsername();
        String password = loginDto.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidLoginCredentialsException::new);

        if (!passwordEncoder.matches(password, user.getPassword()) || !user.isEnabled()) {
            throw new InvalidLoginCredentialsException();
        }

        String token = jwtProcessor.getBuilder()
                .withSubject(user.getEmail())
                .withScopes(List.of("ROLE_"+ Role.USER.name()))
                .build();

        ResponseToken responseToken = new ResponseToken();
        responseToken.setAccessToken(token);

        return responseToken;
    }

    @Override
    public void register(RegisterDto registerDTO) {
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setName(registerDTO.getName());

        userRepository.save(user);

        String token = jwtProcessor.getBuilder()
                .withSubject(registerDTO.getEmail())
                .withClaim("expiredAt", System.currentTimeMillis() + verificationDuration)
                .build();

        userAuthEmailService.sendConfirmationEmail(RegisterTokenDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .token(token)
                .build());
    }

    @Override
    public void confirmRegistration(VerificationTokenDto verificationTokenDto) {
        VerifiedJWT verifiedJWT = jwtProcessor.getVerifiedJWT(verificationTokenDto.getToken());

        Optional<Long> expiredAt = verifiedJWT.getClaim("expiredAt", Long.class);
        if (expiredAt.isEmpty() || expiredAt.get() < System.currentTimeMillis()) {
            throw new RuntimeException("Token expired");
        }

        User user = userRepository.findByEmail(verifiedJWT.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        User user = userRepository.findByEmail(resetPasswordDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtProcessor.getBuilder()
                .withSubject(user.getEmail())
                .withClaim("expiredAt", System.currentTimeMillis() + verificationDuration)
                .build();

        userAuthEmailService.sendResetPasswordEmail(ResetPasswordTokenDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .token(token)
                .build());
    }

    @Override
    public void resetNewPassword(ConfirmNewPasswordTokenDto confirmNewPasswordTokenDto) {
        VerifiedJWT verifiedJWT = jwtProcessor.getVerifiedJWT(confirmNewPasswordTokenDto.getToken());

        Optional<Long> expiredAt = verifiedJWT.getClaim("expiredAt", Long.class);
        if (expiredAt.isEmpty() || expiredAt.get() < System.currentTimeMillis()) {
            throw new RuntimeException("Token expired");
        }

        User user = userRepository.findById(confirmNewPasswordTokenDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!verifiedJWT.getUsername().equals(user.getEmail())) {
            throw new RuntimeException("Invalid token");
        }

        user.setPassword(passwordEncoder.encode(confirmNewPasswordTokenDto.getNewPassword()));
        userRepository.save(user);
    }
}
