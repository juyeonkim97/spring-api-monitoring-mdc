package com.example.monitoring.logging.util;

import com.example.monitoring.logging.type.MDCType;
import org.slf4j.MDC;
import org.slf4j.spi.MDCAdapter;

public class MDCUtil {
    private static MDCAdapter mdc = MDC.getMDCAdapter();
    public static void set(MDCType mdcType, String value) {
        mdc.put(mdcType.name(), value);
    }

    public static void setJsonValue(MDCType mdcType, Object value) {
        try {
            if (value != null) {
                String json = JsonUtils.toJson(value);
                mdc.put(mdcType.name(), json);
            }
        } catch (Exception e) {
            // ignored
        }
    }

    public static String get(MDCType mdcType) {
        return mdc.get(mdcType.name());
    }

    /**
     * 같은 쓰레드를 사용할 때 데이터 혼동이 있을 수 있기 때문에 clear 해줘야 함
     */
    public static void clear() {
        mdc.clear();
    }
}
