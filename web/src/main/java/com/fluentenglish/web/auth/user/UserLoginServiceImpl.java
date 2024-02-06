package com.fluentenglish.web.auth.user;

import com.fluentenglish.web.auth.Role;
import com.fluentenglish.web.auth.dto.LoginDto;
import com.fluentenglish.web.auth.dto.ResponseToken;
import com.fluentenglish.web.auth.exception.InvalidLoginCredentialsException;
import com.fluentenglish.web.auth.token.jwt.JWTProcessor;
import com.fluentenglish.web.user.User;
import com.fluentenglish.web.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLoginServiceImpl implements UserLoginService{
    private final UserRepository userRepository;

    private final JWTProcessor jwtProcessor;

    private final PasswordEncoder passwordEncoder;

    public UserLoginServiceImpl(UserRepository userRepository,
                                JWTProcessor jwtProcessor,
                                PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtProcessor = jwtProcessor;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public ResponseToken login(LoginDto loginDto) {
        String email = loginDto.getUsername();
        String password = loginDto.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidLoginCredentialsException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
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
}
