package com.fluentenglish.web.auth.user.service;

import com.fluentenglish.web.auth.user.dto.RegisterTokenDto;

public interface UserAuthEmailService {
    void sendConfirmationEmail(RegisterTokenDto registerTokenDto);
}
