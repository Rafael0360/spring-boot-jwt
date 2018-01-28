package com.github.diogopeixoto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.diogopeixoto.exception.EntityNotFoundException;
import com.github.diogopeixoto.model.Role;
import com.github.diogopeixoto.repository.RoleRepository;

@Service
public class RoleService {

	private RoleRepository roleRepository;

	public RoleService(RoleRepository roleRepository) {
		super();
		this.roleRepository = roleRepository;
	}

	public void delete(long id) {
		this.roleRepository.delete(id);
	}

	public List<Role> findAll() {
		return this.roleRepository.findAll();
	}

	public Role findById(long id) {
		Role role = this.roleRepository.findOne(id);

		if (role != null) {
			return role;
		} else {
			throw new EntityNotFoundException(String.format("Role with id = %d not found.", id));
		}
	}

	public Role save(Role role, long id) {
		Role roleSearched = this.findById(id);
		role.setId(roleSearched.getId());

		return this.roleRepository.save(role);
	}

	public Role save(Role role) {
		return this.roleRepository.save(role);
	}
}
