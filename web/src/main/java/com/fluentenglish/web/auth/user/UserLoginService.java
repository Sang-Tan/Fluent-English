package com.fluentenglish.web.auth.user;

import com.fluentenglish.web.auth.dto.LoginDto;
import com.fluentenglish.web.auth.dto.ResponseToken;

public interface UserLoginService {
    ResponseToken login(LoginDto loginDto);
}
