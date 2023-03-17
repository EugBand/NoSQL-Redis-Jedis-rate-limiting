package com.epam.nosqlrun.ratelimiter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.nosqlrun.ratelimiter.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findById(Integer userId);

	Optional<User> findByName(String name);
}
