package com.vc.sb.common.aop;

import com.vc.sb.common.enums.ResultCode;
import com.vc.sb.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Author:       VinceChen
 * Date:         2019-10-16 23:01
 * Description:
 */

@Aspect
@Order(0)
@Slf4j
@Component("logAspect")
public class LogAspect {

    @Pointcut("execution(* com.vc.sb.web..*.*(..))")
    public void logPointCut() {
    }

    @Around(value = "logPointCut()")
    public Object logOperate(ProceedingJoinPoint joinPoint) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result;
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String methodName = method.getDeclaringClass().getSimpleName() + "." + method.getName();

        log.info("[log aop] begin; method:{}", methodName);
        try {
            result = joinPoint.proceed();
            log.info("[log aop] end; method:{}, costTime:{}", methodName, stopWatch.getTime());
        } catch (Throwable throwable) {
            log.error("[log aop] exception; method:{}, costTime:{}", methodName, stopWatch.getTime());
            result = new Result(ResultCode.internal_server_error);
        }
        return result;
    }

}
