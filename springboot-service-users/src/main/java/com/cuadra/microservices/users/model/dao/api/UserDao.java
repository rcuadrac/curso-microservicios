package com.cuadra.microservices.users.model.dao.api;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.cuadra.microservices.commons.model.entity.User;


@RepositoryRestResource(path = "users")
public interface UserDao extends PagingAndSortingRepository<User, Long>{
	
	public User findByUsername(@Param("username") String username);
	 

}
