package com.cuadra.microservices.oauth.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cuadra.microservices.commons.model.entity.User;
import com.cuadra.microservices.oauth.clients.UserFeignClient;
import com.cuadra.microservices.oauth.services.api.UserService;

import brave.Tracer;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	private UserFeignClient userFeignClient;
	private Tracer tracer;

	@Autowired
	public UserServiceImpl(UserFeignClient userFeignClient, 
			Tracer tracer) {
		this.userFeignClient = userFeignClient;
		this.tracer = tracer;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {
			User user = userFeignClient.findByUsername(username);
			List<GrantedAuthority> authorities = user.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role.getName()))
					.peek(authority -> log.info("Role " + authority.getAuthority())).collect(Collectors.toList());

			log.info("Authenticated user " + username);

			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					user.getEnabled(), true, true, true, authorities);
		} catch (FeignException e) {
			String errorMessage = "username " + username + " does not exist";
			log.error(errorMessage, e);
			tracer.currentSpan().tag("error.message", errorMessage + ": " + e.getMessage());
			throw new UsernameNotFoundException(errorMessage, e);
		}

	}

	@Override
	public User findByUsername(String username) {
		return userFeignClient.findByUsername(username);
	}

	@Override
	public User update(User user, Long id) {
		return userFeignClient.update(user, id);
	}

}
