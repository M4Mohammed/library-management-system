package com.project.librarymanagementsystem.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Enumeration;

@Slf4j
@Aspect
@Component
public class ApiLoggingAOP {

    private String getHeadersAsString(HttpServletRequest request) {
        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.append(headerName).append(": ").append(request.getHeader(headerName)).append(", ");
        }
        return headers.toString();

    }

    @Before("within(com.project.librarymanagementsystem..*Controller)")
    public void requestLog(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        log.info("API Request - Method: [{}] Path: [{}] IP: [{}]",
                request.getMethod(),
                request.getRequestURI(),
                request.getRemoteAddr());

        log.debug("Request Headers: {}", getHeadersAsString(request));
        log.debug("Request Parameters: {}", request.getParameterMap());
        log.debug("Calling method: {}.{}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "within(com.project.librarymanagementsystem..*Controller)", returning = "result")
    public void responseLog(JoinPoint jp, Object result) {
        if (result instanceof ResponseEntity<?> responseEntity) {
            log.info("API Response - Status: [{}] Body: [{}]",
                    responseEntity.getStatusCode(),
                    responseEntity.getBody());
        } else {
            log.info("API Response - Body: [{}]", result);
        }
                log.debug("Response from method: {}.{}",
                jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName());
    }

    @AfterThrowing(pointcut = "within(com.project.librarymanagementsystem..*Controller)", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.error("Exception in {}.{} - Exception: {} - Message: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                ex.getClass().getName(),
                ex.getMessage());
        log.debug("Exception stack trace:", ex);

    }
}
