package com.example.monitoring.logging.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "log")
public class LogConfig {

    private Level level;

    private Slack slack;

    public boolean isEnabledLogging(ILoggingEvent loggingEvent) {
        return slack.enabled
                && loggingEvent.getLevel().isGreaterOrEqual(level);
    }

    public String getSlackChannel() {
        return slack.channel;
    }

    public String getWebhookUrl() {
        return slack.webHookUrl;
    }

    @Getter
    @Setter
    private static class Slack {
        private boolean enabled;
        private String webHookUrl;
        private String channel;
    }
}

