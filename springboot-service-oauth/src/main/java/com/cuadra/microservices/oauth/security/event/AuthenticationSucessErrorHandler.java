package com.cuadra.microservices.oauth.security.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.cuadra.microservices.commons.model.entity.User;
import com.cuadra.microservices.oauth.services.api.UserService;

import brave.Tracer;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthenticationSucessErrorHandler implements AuthenticationEventPublisher {

	private UserService userService;
	private Tracer tracer;
	

	@Autowired
	public AuthenticationSucessErrorHandler(UserService userService,
			Tracer tracer) {
		this.userService = userService;
		this.tracer = tracer;
	}

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails userDetail = (UserDetails) authentication.getPrincipal();
		log.info("::::: Success Login" + userDetail.getUsername());
		
		User user = userService.findByUsername(authentication.getName());
		if(user.getAttempts() != null && user.getAttempts() > 0) {
			user.setAttempts(0);
			userService.update(user, user.getId());
		}
		
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String errorMessage = ":::: Error en login \n" + exception.getMessage();
		log.error(errorMessage);
		try {
			StringBuilder errors = new StringBuilder();
			errors.append(errorMessage);
			User user = userService.findByUsername(authentication.getName());
			if (user.getAttempts() == null) {
				user.setAttempts(0);
			}
			log.info(":::: Current attempts :::: " + user.getAttempts());
			user.setAttempts(user.getAttempts() + 1);

			if (user.getAttempts() >= 3) {
				log.info(String.format("User %s will be disabled due to get maximium attemts", user.getUsername()));
				user.setEnabled(false);
			}
			userService.update(user, user.getId());
			tracer.currentSpan().tag("error.message", errors.toString());

		} catch (FeignException e) {
			log.error("::: User does not exists::: ");
		}

	}

}
