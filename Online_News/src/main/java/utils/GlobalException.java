package utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String method = ex.getMethod();
        String[] supportedMethods = ex.getSupportedMethods();
        Map<String, Object> map = new HashMap<>();
        map.put("method:",ex.getMethod());
        map.put("code", status.value());
        map.put("timestamp", Instant.now().getEpochSecond());
        map.put("error:",ex.getClass().getName());
        map.put("message", "不支持的请求类型：" + method );
        return super.handleExceptionInternal(ex, map, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("method:",ex.getHttpMethod());
        map.put("code:", status.value());
        map.put("timestamp", Instant.now().getEpochSecond());
        map.put("path",ex.getRequestURL());
        map.put("message",ex.getMessage());
        map.put("error:",ex.getClass().getName());
        return super.handleExceptionInternal(ex, map, headers, status, request);
    }



}