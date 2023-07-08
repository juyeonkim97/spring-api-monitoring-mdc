package com.example.monitoring.logging.filters;

import com.example.monitoring.logging.type.MDCType;
import com.example.monitoring.logging.util.MDCUtil;
import com.example.monitoring.logging.util.RequestWrapper;

import javax.servlet.*;
import java.io.IOException;

/**
 * ServletRequest 가지고 MDC 설정
 */
public class LogbackMdcFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestWrapper requestWrapper = new RequestWrapper(request);

        MDCUtil.setJsonValue(MDCType.PARAMETER_MAP_MDC, requestWrapper.getParameterMap());
        MDCUtil.set(MDCType.REQUEST_BODY_MDC, requestWrapper.getBody());
        MDCUtil.set(MDCType.REQUEST_URI_MDC, requestWrapper.getRequestUri());
        MDCUtil.set(MDCType.REQUEST_METHOD_MDC, requestWrapper.getMethod());
        MDCUtil.set(MDCType.TRANSACTION_ID, requestWrapper.getTransactionId());

        chain.doFilter(request, response);
    }
}
