package com.xpp.gaia.boot.trace;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * 计数器工厂
 *
 * @author Akira
 * @since 2022/6/21
 */
public class CounterFactory {

    private static Counter totalCount;
    private static Counter warningCount;
    private static Counter exceptionCount;

    protected static void build(MeterRegistry meterRegistry) {
        totalCount = meterRegistry.counter("app_requests_count", "gaia", "core");
        warningCount = meterRegistry.counter("app_requests_warning_count", "gaia", "core");
        exceptionCount = meterRegistry.counter("app_requests_exception_count", "gaia", "core");
    }

    public static Counter getTotalCount() {
        return totalCount;
    }

    public static Counter getWarningCount() {
        return warningCount;
    }

    public static Counter getExceptionCount() {
        return exceptionCount;
    }

}
