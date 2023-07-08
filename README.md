# spring-api-monitoring-mdc

## API 에러 모니터링 슬랙 전송
- LogbackMdcFilter
- MultiReadableHttpServletRequestFilter
- CustomLogbackAppender

## application.yml 추가
```
log:
  slack:
    enabled: true
    webHookUrl: https://hooks.slack.com/services/T05GD0...
    channel: 채널이름
  level: ERROR
```
