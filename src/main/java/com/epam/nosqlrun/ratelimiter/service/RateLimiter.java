package com.epam.nosqlrun.ratelimiter.service;

import java.time.Duration;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.nosqlrun.ratelimiter.model.TenantById;
import com.epam.nosqlrun.ratelimiter.model.TenantByIp;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;

@Service
public class RateLimiter {

    @Autowired
    UserService userService;

    @Autowired
    ProxyManager<String> proxyManager;

    public Bucket resolveBucketForId(String key) {
        Supplier<BucketConfiguration> configSupplier = getConfigSupplierForUserId(key);

        return proxyManager.builder().build(key, configSupplier);
    }

    public Bucket resolveBucketForIp(String key) {
        Supplier<BucketConfiguration> configSupplier = getConfigSupplierForUserIp(key);

        return proxyManager.builder().build(key, configSupplier);
    }

    private Supplier<BucketConfiguration> getConfigSupplierForUserId(String userId) {
        TenantById tenantById = userService.getTenantById(userId);

        Refill refill = Refill.intervally(tenantById.getAllowedRequests(), Duration.ofSeconds(tenantById.getTimeInterval()));
        Bandwidth limit = Bandwidth.classic(tenantById.getAllowedRequests(), refill);
        return () -> (BucketConfiguration.builder()
                .addLimit(limit)
                .build());
    }

    private Supplier<BucketConfiguration> getConfigSupplierForUserIp(String userId) {
        TenantByIp tenantByIp = userService.getTenantByIp(userId);

        Refill refill = Refill.intervally(tenantByIp.getAllowedRequests(), Duration.ofSeconds(tenantByIp.getTimeInterval()));
        Bandwidth limit = Bandwidth.classic(tenantByIp.getAllowedRequests(), refill);
        return () -> (BucketConfiguration.builder()
                .addLimit(limit)
                .build());
    }

}
