package com.epam.nosqlrun.ratelimiter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.nosqlrun.ratelimiter.model.TenantById;

@Repository
public interface TenantByIdRepository extends JpaRepository<TenantById, Integer> {

	Optional<TenantById> findById(Integer tenantId);

	Optional<TenantById> findByAccountId(String accountId);
}
