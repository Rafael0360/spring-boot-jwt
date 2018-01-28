package com.github.diogopeixoto.controller;

import java.util.List;
import java.util.Set;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.diogopeixoto.model.Role;
import com.github.diogopeixoto.model.User;
import com.github.diogopeixoto.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> findAll() {
		return this.userService.findAll();
	}

	@GetMapping(value = "/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public User findById(@PathVariable("id") long id) {
		return this.userService.findById(id);
	}

	@PostMapping("/sign-up")
	public void signUp(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userService.save(user);
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public User updateRoles(@PathVariable("id") long id, @RequestBody Set<Role> roles) {
		return this.userService.updateRoles(id, roles);
	}
}
