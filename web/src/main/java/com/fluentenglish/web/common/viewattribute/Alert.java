package com.fluentenglish.web.common.viewattribute;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Alert {
    private final List<String> error = new ArrayList<>();
    private final List<String> warning = new ArrayList<>();
    private final List<String> info = new ArrayList<>();
}
