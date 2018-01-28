package com.github.diogopeixoto.security;

import org.springframework.security.core.GrantedAuthority;

class RoleAuthority implements GrantedAuthority {

	private static final long serialVersionUID = -3665182900400869432L;

	private String authority;

	public RoleAuthority(String authority) {
		super();
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}

	@Override
	public String toString() {
		return "RoleAuthority [authority=" + authority + "]";
	}
}
