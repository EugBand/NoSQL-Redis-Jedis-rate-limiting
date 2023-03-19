package com.epam.nosqlrun.ratelimiter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.nosqlrun.ratelimiter.model.TenantByIp;

@Repository
public interface TenantByIpRepository extends JpaRepository<TenantByIp, Integer> {

	Optional<TenantByIp> findById(Integer tenantId);

	Optional<TenantByIp> findByAccountIp(String ip);
}
