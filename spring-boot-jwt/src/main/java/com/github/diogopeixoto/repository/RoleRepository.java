package com.github.diogopeixoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.diogopeixoto.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
