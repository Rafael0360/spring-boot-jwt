package com.github.diogopeixoto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.diogopeixoto.model.Role;
import com.github.diogopeixoto.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private UserService userService;

	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		} else {
			List<GrantedAuthority> authorities = getAuthorities(user.getRoles());

			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					authorities);
		}
	}

	public List<GrantedAuthority> getAuthorities(Set<Role> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>(roles.size());

		roles.forEach(r -> {
			authorities.add(new GrantedAuthority() {
				private static final long serialVersionUID = -2059056411808213267L;

				@Override
				public String getAuthority() {
					return r.getName();
				}
			});
		});
		return authorities;
	}
}