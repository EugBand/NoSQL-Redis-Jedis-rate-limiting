package com.epam.nosqlrun.ratelimiter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController {

	@GetMapping("/v1/user")
	public String getUser() {
		return "Hello Secure user";
	}
	
	@GetMapping("/v2/user")
	public String getUserNotsecure() {
		return "Hello Not Secure user";
	}
}
