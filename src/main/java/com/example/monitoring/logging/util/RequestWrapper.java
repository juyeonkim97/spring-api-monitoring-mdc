package com.example.monitoring.logging.util;

import com.google.common.io.CharStreams;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;

public class RequestWrapper {

    private HttpServletRequest request;

    public RequestWrapper(ServletRequest request) {
        this.request = (HttpServletRequest) request;
    }

    public Map<String, String> getParameterMap() {
        Map<String, String> convertedParameterMap = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();

        for (String key : parameterMap.keySet()) {
            String[] values = parameterMap.get(key);
            StringJoiner valueString = new StringJoiner(",");

            for (String value : values) {
                valueString.add(value);
            }

            convertedParameterMap.put(key, valueString.toString());
        }
        return convertedParameterMap;
    }

    public String getRequestUri() {
        return request.getRequestURI();
    }

    public String getBody() {
        try {
            return CharStreams.toString(request.getReader());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getMethod() {
        return request.getMethod();
    }

    public String getTransactionId() {
        return UUID.randomUUID().toString();
    }
}

