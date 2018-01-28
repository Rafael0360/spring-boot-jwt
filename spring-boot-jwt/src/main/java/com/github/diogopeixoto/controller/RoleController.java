package com.github.diogopeixoto.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.diogopeixoto.model.Role;
import com.github.diogopeixoto.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {

	private RoleService roleService;

	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void create(@RequestBody Role role) {
		this.roleService.save(role);
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void delete(@PathVariable("id") long id) {
		this.roleService.delete(id);
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Role> findAll() {
		return this.roleService.findAll();
	}

	@GetMapping(value = "/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Role findById(@PathVariable("id") long id) {
		return this.roleService.findById(id);
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void update(@PathVariable("id") long id, @RequestBody Role role) {
		this.roleService.save(role, id);
	}
}
