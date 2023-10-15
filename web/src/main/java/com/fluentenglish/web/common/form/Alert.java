package com.fluentenglish.web.common.form;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class Alert {
    private String message;
    private AlertType type;
}
