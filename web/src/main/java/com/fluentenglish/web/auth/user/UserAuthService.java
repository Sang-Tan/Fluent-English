package com.fluentenglish.web.auth.user;

import com.fluentenglish.web.auth.dto.LoginDto;
import com.fluentenglish.web.auth.user.dto.RegisterDto;
import com.fluentenglish.web.auth.dto.ResponseToken;
import com.fluentenglish.web.auth.user.dto.VerificationTokenDto;

public interface UserAuthService {
    ResponseToken login(LoginDto loginDto);

    void register(RegisterDto registerDTO);

    void confirmRegistration(VerificationTokenDto verificationTokenDto);
}
