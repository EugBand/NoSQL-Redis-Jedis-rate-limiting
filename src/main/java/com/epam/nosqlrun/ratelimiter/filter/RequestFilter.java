package com.epam.nosqlrun.ratelimiter.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.epam.nosqlrun.ratelimiter.service.RateLimiter;

import io.github.bucket4j.Bucket;
import io.micrometer.core.instrument.util.StringUtils;

@Component
public class RequestFilter extends OncePerRequestFilter {

    @Autowired
    RateLimiter rateLimiter;

    //    @Override
    //    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    //            throws ServletException, IOException {
    //
    //        if (request.getRequestURI().startsWith("/v1")) {
    //            String tenantId = request.getHeader("Account-id");
    //            String tenantIp = request.getHeader("Account-ip");
    //            if (StringUtils.isNotBlank(tenantId) || StringUtils.isNotBlank(tenantIp)) {
    //                Bucket bucket = rateLimiter.resolveBucketForId(tenantId);
    //                if (bucket.tryConsume(1)) {
    //                    filterChain.doFilter(request, response);
    //                } else {
    //                    sendErrorReponse(response, HttpStatus.TOO_MANY_REQUESTS.value());
    //                }
    //            } else {
    //                sendErrorReponse(response, HttpStatus.FORBIDDEN.value());
    //            }
    //        } else {
    //            filterChain.doFilter(request, response);
    //        }
    //    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/v1")) {
            String tenantId = request.getHeader("Tenant-id");
            String tenantIp = request.getHeader("Tenant-ip");
            if (StringUtils.isBlank(tenantId) && StringUtils.isBlank(tenantIp)) {
                sendErrorReponse(response, HttpStatus.FORBIDDEN.value());
                return;
            }
            if (StringUtils.isNotBlank(tenantId)) {
                Bucket bucket = rateLimiter.resolveBucketForId(tenantId);
                processTenantHeader(request, response, filterChain, bucket);
            } else if (StringUtils.isNotBlank(tenantIp)) {
                Bucket bucket = rateLimiter.resolveBucketForIp(tenantIp);
                processTenantHeader(request, response, filterChain, bucket);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private void processTenantHeader(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Bucket bucket)
            throws IOException, ServletException {
        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            sendErrorReponse(response, HttpStatus.TOO_MANY_REQUESTS.value());
        }
    }

    private void sendErrorReponse(HttpServletResponse response, int value) {
        response.setStatus(value);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

}
