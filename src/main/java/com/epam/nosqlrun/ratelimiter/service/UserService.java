package com.epam.nosqlrun.ratelimiter.service;

import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.epam.nosqlrun.ratelimiter.model.TenantById;
import com.epam.nosqlrun.ratelimiter.exception.UserNotFoundException;
import com.epam.nosqlrun.ratelimiter.model.TenantByIp;
import com.epam.nosqlrun.ratelimiter.repository.TenantByIdRepository;
import com.epam.nosqlrun.ratelimiter.repository.TenantByIpRepository;

@Service
public class UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	TenantByIdRepository tenantByIdRepository;

	@Autowired
	TenantByIpRepository tenantByIpRepository;

	@Cacheable(value="userList", key="#tenantId")
	public TenantById getTenantById(String  tenantId) {
		Optional<TenantById> userOp = tenantByIdRepository.findByAccountId(tenantId);
		if(userOp.isPresent()) {
			return userOp.get();
		} else {
			throw new UserNotFoundException(String.format("TenantById with name %s not found", tenantId));
		}
	}

	@Cacheable(value="userList", key="#tenantIp")
	public TenantByIp getTenantByIp(String  tenantIp) {
		Optional<TenantByIp> userOp = tenantByIpRepository.findByAccountIp(tenantIp);
		if(userOp.isPresent()) {
			return userOp.get();
		} else {
			throw new UserNotFoundException(String.format("TenantById with name %s not found", tenantIp));
		}
	}
	
	@CacheEvict(value="userList", allEntries = true)
	@Scheduled(fixedDelayString = "${caching.spring.userListTTL}", initialDelay = 50000)
	public void deleteUserList() {
		LOG.info("Evict TenantById List");
	}
}
