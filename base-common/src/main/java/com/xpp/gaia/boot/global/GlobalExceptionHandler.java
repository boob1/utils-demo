package com.xpp.gaia.boot.global;

/**
 * 全局异常处理
 *
 * @author Akira
 * @since 2021/11/9
 */

import static com.xpp.gaia.boot.global.GlobalInterceptor.LOG_SYMBOL_STEP;
import static com.xpp.gaia.boot.global.GlobalInterceptor.traceVar;

import com.xpp.gaia.boot.resubmit.ResubmitException;
import com.xpp.gaia.mybatis.MybatisProcessExcetion;
import com.xpp.gaia.toolkit.ActionResult;
import com.xpp.gaia.toolkit.action.ActionProcessException;
import com.xpp.gaia.toolkit.utils.JsonUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
@Configuration
@Slf4j
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
        log.info("Initializing Gaia GlobalExceptionHandler");
    }

    protected void clearGlobalMeterWarning() {
        GlobalInterceptor.clearGlobalVar();
        // CounterFactory.getWarningCount().increment();
    }

    protected void clearGlobalMeterException() {
        GlobalInterceptor.clearGlobalVar();
        // CounterFactory.getExceptionCount().increment();
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ActionResult<Map<String, Object>> handleMethodArgumentTypeMismatch(
        MethodArgumentTypeMismatchException ex,
        HttpServletRequest request) {

        // 构建详细的错误信息
        Map<String, Object> errorDetails = new LinkedHashMap<>(8);
        errorDetails.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        errorDetails.put("httpMethod", request.getMethod());
        errorDetails.put("requestUri", request.getRequestURI());
        errorDetails.put("parameterName", ex.getName());
        errorDetails.put("rejectedValue", ex.getValue());
        errorDetails.put("expectedType", ex.getRequiredType() != null ? ex.getRequiredType().getName() : "Unknown");

        // 提取根本原因
        Throwable rootCause = ex.getCause();
        if (rootCause != null) {
            errorDetails.put("errorType", rootCause.getClass().getName());
            errorDetails.put("errorMessage", rootCause.getMessage());
        } else {
            errorDetails.put("errorMessage", ex.getMessage());
        }

        // 打印完整的堆栈跟踪到日志
        log.error("{} 参数类型转换失败: {}--{}", LOG_SYMBOL_STEP, JsonUtil.toJson(errorDetails), ex);

        // 构建友好的用户错误信息
        Map<String, Object> userErrorMap = new HashMap<>(2);
        userErrorMap.put("field", ex.getName());
        userErrorMap.put("message", String.format("参数 '%s' 格式不正确。需要 %s 类型，实际接收到 '%s'",
            ex.getName(),
            ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "数字",
            ex.getValue()));

        clearGlobalMeterWarning();

        return ActionResult.build(
            String.valueOf(HttpStatus.BAD_REQUEST.value()),
            userErrorMap,
            "参数格式错误");
    }

    /**
     * 在@Valid或@Validated注解校验时发生
     * ModelAttributeMethodProcessor类对参数进行绑定到方法对象中，并对带有@Valid或@Validated注解的参数进行参数校验
     * ModelAttributeMethodProcessor.resolveArgument 会抛出BindException
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ActionResult<Map<String, Object>> handlerBindException(Exception exception) {
        BindingResult result = ((BindException) exception).getBindingResult();
        Map<String, Object> errorFieldMap;
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            errorFieldMap = new HashMap<>(fieldErrors.size());
            fieldErrors.forEach(error -> {
                errorFieldMap.put(error.getField(), error.getDefaultMessage());
            });
        } else {
            errorFieldMap = Collections.EMPTY_MAP;
        }
        log.warn("{} 参数校验不合法: {}", LOG_SYMBOL_STEP, JsonUtil.toJson(errorFieldMap));
        clearGlobalMeterWarning();
        return ActionResult.build(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                errorFieldMap,
                "参数错误不合法");
    }

    /**
     * 在@Valid注解校验时发生
     * <url>https://www.baeldung.com/global-error-handler-in-a-spring-rest-api</url>
     *
     * @param constraintViolationException
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ActionResult<Map<String, Object>> handleConstraintViolation(
            ConstraintViolationException constraintViolationException) {
        Map<String, Object> errorFieldMap;
        if (constraintViolationException.getConstraintViolations().size() > 0) {
            errorFieldMap = new HashMap<>(constraintViolationException.getConstraintViolations().size());
            for (ConstraintViolation<?> violation : constraintViolationException.getConstraintViolations()) {
                String[] paths = violation.getPropertyPath().toString().split("\\.");
                errorFieldMap.put(paths[paths.length - 1], violation.getMessage());
                log.debug("{} " + violation.getRootBeanClass().getName() + " " +
                        violation.getPropertyPath() + ": " + violation.getMessage(), LOG_SYMBOL_STEP);
            }
        } else {
            errorFieldMap = Collections.EMPTY_MAP;
        }
        log.warn("{} 参数校验不合法: {}", LOG_SYMBOL_STEP, JsonUtil.toJson(errorFieldMap));
        clearGlobalMeterWarning();
        return ActionResult.build(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                errorFieldMap,
                "参数不合法");
    }

    @ExceptionHandler(ResubmitException.class)
    public ActionResult<String> handleRuntimeException(ResubmitException resubmitException) {
        log.warn("{} 捕获重复提交: {}", LOG_SYMBOL_STEP, resubmitException.getResubmitData());
        String threadName = traceVar.get();
        clearGlobalMeterWarning();
        return ActionResult.build(
                resubmitException.code,
                null,
                threadName,
                String.format("%s", resubmitException.getMessage()));
    }

    @ExceptionHandler(ActionProcessException.class)
    public ActionResult<?> handleActionProcessException(ActionProcessException actionProcessException) {
        log.error(LOG_SYMBOL_STEP+"捕获程序运行时已定义异常: "+actionProcessException.getCode()+ actionProcessException);
        String threadName = traceVar.get();
        clearGlobalMeterException();
        return ActionResult.build(
                actionProcessException.getCode() == null ?
                        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()) : actionProcessException.getCode(),
                null,
                threadName,
                actionProcessException.getMessage());
    }

    @ExceptionHandler(MybatisProcessExcetion.class)
    public ActionResult<?> handleMybatisProcessException(MybatisProcessExcetion mybatisProcessExcetion) {
        log.error(LOG_SYMBOL_STEP+"捕获Sql运行时已定义异常，具体请根据线程id查看sql日志文件: [{}]{}" , mybatisProcessExcetion.getCode(), mybatisProcessExcetion.getMessage());
        String threadName = traceVar.get();
        clearGlobalMeterException();
        return ActionResult.build(
                mybatisProcessExcetion.getCode() == null ?
                        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()) : mybatisProcessExcetion.getCode(),
                null,
                threadName,
                mybatisProcessExcetion.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ActionResult<?> handleRuntimeException(RuntimeException runtimeException) {
        String formedMessage = runtimeException.getMessage();
        if (runtimeException instanceof MaxUploadSizeExceededException) {
            formedMessage = "上传文件超出了规定大小";
        }
        log.error(LOG_SYMBOL_STEP+"捕获程序运行时未定义异常: ", runtimeException);
        String threadName = traceVar.get();
        clearGlobalMeterException();
        return ActionResult.build(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                null,
                threadName,
                formedMessage);
    }

}
