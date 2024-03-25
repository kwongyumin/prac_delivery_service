package org.delivery.api.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class LoggerFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        var requestMapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        var responseMapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
        log.info("INIT URI : {}", requestMapper.getRequestURI());

        // 실행 전
        chain.doFilter(requestMapper, responseMapper);
        // 실행 후

        // 요청 정보
        var headerNames = requestMapper.getHeaderNames();
        var headerValues = new StringBuilder();

        headerNames.asIterator().forEachRemaining( headerKey -> {
            var headerValue = requestMapper.getHeader(headerKey);
            headerValues
                    .append("[")
                    .append(headerKey)
                    .append(" : ")
                    .append(headerValue)
                    .append(" , ")
                    .append("] ");
        });

        var requestBody = new String(requestMapper.getContentAsByteArray());
        var uri = requestMapper.getRequestURI();
        var method = requestMapper.getMethod();

        log.info(" >>>>> uri : {} , method : {} , header : {} , body : {}",uri, method ,headerValues, requestBody);

        // 응답 정보
        var responseHeaderValues = new StringBuilder();

        responseMapper.getHeaderNames().forEach(headerKey -> {
            var headerValue = responseMapper.getHeader(headerKey);
            responseHeaderValues
                    .append("[")
                    .append(headerKey)
                    .append(" : ")
                    .append(headerValue)
                    .append(" , ")
                    .append("] ");
        });

        var responseBody = new String(responseMapper.getContentAsByteArray());

        log.info(" <<<<< uri : {} , method : {} , header : {} , body : {}",uri, method ,responseHeaderValues, responseBody);
        // responseBody 초기화 (필수)
        responseMapper.copyBodyToResponse();
    }
}
