package com.github.diogopeixoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.diogopeixoto.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
