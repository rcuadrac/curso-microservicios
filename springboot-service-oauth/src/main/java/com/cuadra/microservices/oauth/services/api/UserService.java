package com.cuadra.microservices.oauth.services.api;

import com.cuadra.microservices.commons.model.entity.User;

public interface UserService {
	
	User findByUsername(String username);
	User update(User user, Long id);

}
