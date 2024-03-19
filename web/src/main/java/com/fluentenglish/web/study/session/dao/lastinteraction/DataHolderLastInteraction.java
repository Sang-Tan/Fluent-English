package com.fluentenglish.web.study.session.dao.lastinteraction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataHolderLastInteraction implements SessionLastInteraction {
    private long lastInteractionTime;
}
