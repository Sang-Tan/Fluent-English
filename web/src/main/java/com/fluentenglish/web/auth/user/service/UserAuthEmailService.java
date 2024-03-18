package com.fluentenglish.web.auth.user.service;

import com.fluentenglish.web.auth.user.dto.RegisterTokenDto;
import com.fluentenglish.web.auth.user.dto.ResetPasswordTokenDto;

public interface UserAuthEmailService {
    void sendConfirmationEmail(RegisterTokenDto registerTokenDto);

    void sendResetPasswordEmail(ResetPasswordTokenDto build);
}
