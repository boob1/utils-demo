package com.xpp.gaia.boot.trace;

import static com.xpp.gaia.boot.global.GlobalInterceptor.LOG_SYMBOL_STEP;
import static com.xpp.gaia.boot.global.GlobalInterceptor.traceVar;
import static com.xpp.gaia.boot.measure.MeasureInterceptor.MEASURE_MAP_KEY_ERROR;
import static com.xpp.gaia.boot.measure.MeasureInterceptor.MEASURE_MAP_KEY_META;

import com.xpp.gaia.boot.measure.MeasureInterceptor;
import com.xpp.gaia.toolkit.ActionResult;
import com.xpp.gaia.toolkit.action.ActionProcessException;
import com.xpp.gaia.toolkit.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Meter Aspect
 *
 * @author Akira
 * @since 2021/9/6
 */
@Aspect
@Component
@Slf4j
public class TraceAdvice {

    // @Autowired
    // MeterRegistry registry;

    public TraceAdvice() {
        log.info("Initializing Gaia TraceAdvice");
    }

    /**
     * 由measure模块替代
     */
    // @PostConstruct
    // public void init() {
    //     CounterFactory.build(registry);
    // }
    @Pointcut("@annotation(Meter)")
    public void meterCut() {
    }

    @Pointcut("@annotation(Trace)")
    public void traceCut() {
    }

    @Before("meterCut() && @annotation(meter)")
    public void before(JoinPoint joinPoint, Meter meter) {
        // 关键指标项
        if (StringUtils.isNotBlank(meter.KMI())) {
            MeasureInterceptor.measureVar.get().put(MEASURE_MAP_KEY_META, meter.KMI());
            return;
        }
    }

    @AfterReturning(returning = "returnValue", pointcut = "traceCut()")
    public void afterReturning(JoinPoint joinPoint, Object returnValue) {
        if (returnValue instanceof ActionResult) {
            // 确保只打印一次
            if (!((ActionResult<?>) returnValue).isSuccess()
                    && ((ActionResult<?>) returnValue).getThreadHold() != null) {
                ((ActionResult<?>) returnValue).setThreadHold(traceVar.get());
                MeasureInterceptor.measureVar.get().put(MEASURE_MAP_KEY_ERROR, "1");
                log.error("{} 程序执行捕获返回值错误信息: {}", LOG_SYMBOL_STEP, JsonUtil.toJson(returnValue));
            }
        }
    }

    @AfterThrowing(throwing = "exception", pointcut = "traceCut()")
    public void afterThrowing(Throwable exception) {
        // 其他交由全局拦截器处理异常
        if (exception instanceof ActionProcessException
                && ((ActionProcessException) exception).getThreadHold() != null) {
            ((ActionProcessException) exception).setThreadHold(traceVar.get());
            // log.error("{} 程序执行出现异常: {}", LOG_SYMBOL_STEP, exception.getMessage());
        }
    }
}
