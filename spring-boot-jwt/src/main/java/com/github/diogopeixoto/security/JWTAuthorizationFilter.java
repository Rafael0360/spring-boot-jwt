package com.github.diogopeixoto.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.github.diogopeixoto.model.Role;
import com.github.diogopeixoto.model.User;
import com.github.diogopeixoto.service.UserService;

import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private UserService userService;

	public JWTAuthorizationFilter(AuthenticationManager authManager, UserService userService) {
		super(authManager);
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(SecurityConstants.HEADER_STRING);

		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(req, res);
		} else {
			UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(req, res);
		}
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		UsernamePasswordAuthenticationToken authentication = null;

		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		if (token != null) {
			String user = Jwts.parser().setSigningKey(SecurityConstants.SECRET.getBytes())
					.parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, "")).getBody().getSubject();

			if (user != null) {
				List<RoleAuthority> authorities = getAuthorities(user);
				authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
			}
		}
		return authentication;
	}

	public List<RoleAuthority> getAuthorities(String username) {
		User user = userService.findByUsername(username);
		Set<Role> roles = user.getRoles();

		List<RoleAuthority> authorities = new ArrayList<>(roles.size());

		roles.forEach(r -> {
			authorities.add(new RoleAuthority(r.getName()));
		});
		return authorities;
	}
}