package com.example.monitoring.logging.model;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.example.monitoring.logging.type.MDCType;
import com.example.monitoring.logging.util.MDCUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ErrorLog {

    private static final String KIBANA_URL = "http://localhost:5601/app/discover#/";

    private String requestUrl;

    private String message;

    private String logLink;

    public ErrorLog(ILoggingEvent loggingEvent) {
        this.requestUrl = MDCUtil.get(MDCType.REQUEST_METHOD_MDC) + " " + MDCUtil.get(MDCType.REQUEST_URI_MDC);
        this.message = loggingEvent.getFormattedMessage();
        this.logLink = generateLogLink(MDCUtil.get(MDCType.TRANSACTION_ID));
    }

    private String generateLogLink(String transactionId) {
        String link = """
                <%s?_g=(time:(from:now-1d,to:now))\
                &_a=(columns:!(message),query:(language:kuery,query:'TRANSACTION_ID%%20:%s'))\
                |Kibana 바로가기>
                """;
        return String.format(link,
                KIBANA_URL,
                transactionId);
    }
}
