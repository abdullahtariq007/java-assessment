package company.tap.java.assessment.aspect;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;

@Component
@Slf4j
@Aspect
public class LoggingHandler {

    @Pointcut("execution(* company.tap.java.assessment.controller.*.*(..))")
    public void controller() {
    }

    @Before("controller()")
    public void logBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();
        log.info("Entering in Method :  " + joinPoint.getSignature().getName());
        log.info("Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
        log.info("Arguments :  " + Arrays.toString(joinPoint.getArgs()));
        log.info("Target class : " + joinPoint.getTarget().getClass().getName());
        if (null != request) {
            log.info("Start Header Section of request ");
            log.info("Method Type : " + request.getMethod());
            Enumeration headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = String.valueOf(headerNames.nextElement());
                String headerValue = request.getHeader(headerName);
                log.info("Header Name: " + headerName + " Header Value : " + headerValue);
            }
            log.info("Request Path info :" + request.getServletPath());
            log.info("End Header Section of request ");
        }
    }

//    @Around("controller()")
//    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        long start = System.currentTimeMillis();
//        try {
//            String className = joinPoint.getSignature().getDeclaringTypeName();
//            String methodName = joinPoint.getSignature().getName();
//            Object result = joinPoint.proceed();
//            long elapsedTime = System.currentTimeMillis() - start;
//            log.debug("Method " + className + "." + methodName + " ()" + " execution time : "
//                    + elapsedTime + " ms");
//
//            return result;
//        } catch (IllegalArgumentException e) {
//            log.error("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in "
//                    + joinPoint.getSignature().getName() + "()");
//            throw e;
//        }
//    }
//
    @AfterReturning(pointcut ="controller()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
//        Object result = new Object();
//        HttpServletResponse result =
        String returnValue = this.getValue(result);
        log.info("Method Return value : " + returnValue);
    }
    private String getValue(Object result) {
        String returnValue = null;
        if (null != result) {
            if (result.toString().endsWith("@" + Integer.toHexString(result.hashCode()))) {
                returnValue = ReflectionToStringBuilder.toString(result);
            } else {
                returnValue = result.toString();
            }
        }
        return returnValue;
    }
}