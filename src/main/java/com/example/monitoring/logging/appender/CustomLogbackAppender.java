package com.example.monitoring.logging.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.example.monitoring.logging.config.LogConfig;
import com.example.monitoring.logging.model.ErrorLog;
import com.example.monitoring.logging.model.SlackFieldType;
import com.example.monitoring.logging.util.MDCUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackAttachment;
import net.gpedro.integrations.slack.SlackField;
import net.gpedro.integrations.slack.SlackMessage;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomLogbackAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private static final String FALLBACK_MESSAGE = "에러발생!! 확인요망";

    private final LogConfig logConfig;

    @Override
    public void doAppend(ILoggingEvent eventObject) {
        super.doAppend(eventObject);
    }

    @Override
    protected void append(ILoggingEvent loggingEvent) {
        if (logConfig.isEnabledLogging(loggingEvent)) {
            ErrorLog errorLog = new ErrorLog(loggingEvent);
            toSlack(errorLog);
        }
        MDCUtil.clear();
    }

    private void toSlack(ErrorLog errorLog) {

        SlackApi slackApi = new SlackApi(logConfig.getWebhookUrl());

        var slackAttachment = new SlackAttachment();
        slackAttachment.setFallback(FALLBACK_MESSAGE);

        List<SlackField> fields = mapErrorLogToSlackMessage(errorLog);
        slackAttachment.setFields(fields);

        SlackMessage slackMessage = new SlackMessage("");
        slackMessage.setChannel(logConfig.getSlackChannel());
        slackMessage.setAttachments(Collections.singletonList(slackAttachment));

        slackApi.call(slackMessage);
    }

    private List<SlackField> mapErrorLogToSlackMessage(ErrorLog errorLog) {

        return Arrays.stream(SlackFieldType.values()).map(field -> {

            SlackField body = new SlackField();
            body.addAllowedMarkdown("text"); // 링크 삽입을 위한 마크다운 설정
            body.setTitle(field.getTitle());
            body.setShorten(field.isShorten());

            // field value 설정
            try {
                Field errorLogField = errorLog.getClass().getDeclaredField(field.getFieldName());
                errorLogField.setAccessible(true);
                String value = (String) errorLogField.get(errorLog);
                body.setValue(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return body;
        }).toList();
    }
}