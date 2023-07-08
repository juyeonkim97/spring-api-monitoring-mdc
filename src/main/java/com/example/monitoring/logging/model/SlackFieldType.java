package com.example.monitoring.logging.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SlackFieldType {

    MESSAGE("에러내용", "message", false),
    REQUEST_URL("요청 URL", "requestUrl", false),
    LOG_LINK("Full Log", "logLink", false),
    ;

    private String title;
    private String fieldName;
    private boolean shorten;
}
