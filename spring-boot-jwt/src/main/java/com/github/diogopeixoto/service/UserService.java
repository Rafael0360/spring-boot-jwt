package com.github.diogopeixoto.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.github.diogopeixoto.exception.EntityNotFoundException;
import com.github.diogopeixoto.model.Role;
import com.github.diogopeixoto.model.User;
import com.github.diogopeixoto.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	public User findById(long id) {
		User user = this.userRepository.findOne(id);

		if (user != null) {
			return user;
		} else {
			throw new EntityNotFoundException(String.format("User with id = %d not found.", id));
		}
	}

	public User findByUsername(String username) {
		User user = this.userRepository.findByUsername(username);

		if (user != null) {
			return user;
		} else {
			throw new EntityNotFoundException(String.format("User with username = %s not found.", username));
		}
	}

	public User save(User user) {
		return this.userRepository.save(user);
	}

	public User updateRoles(long id, Set<Role> roles) {
		User user = this.findById(id);

		if (user != null) {
			user.setRoles(roles);

			return this.save(user);
		} else {
			throw new EntityNotFoundException(String.format("User with id = %d not found.", id));
		}
	}
}
