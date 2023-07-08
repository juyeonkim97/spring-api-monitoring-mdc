package com.example.monitoring.logging.config;

import ch.qos.logback.classic.LoggerContext;
import com.example.monitoring.logging.appender.CustomLogbackAppender;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LogContextConfig implements InitializingBean {

    private final CustomLogbackAppender customLogbackAppender;

    @Override
    public void afterPropertiesSet() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        customLogbackAppender.setContext(loggerContext);
        customLogbackAppender.setName("customLogbackAppender");
        customLogbackAppender.start();
        loggerContext.getLogger("ROOT").addAppender(customLogbackAppender);
    }
}
