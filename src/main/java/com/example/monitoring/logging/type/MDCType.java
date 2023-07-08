package com.example.monitoring.logging.type;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MDCType {

    REQUEST_BODY_MDC("request body"),
    PARAMETER_MAP_MDC("request param"),
    REQUEST_URI_MDC("request uri"),
    REQUEST_METHOD_MDC("request method"),
    TRANSACTION_ID("custom transaction id");


    private String description;
}
