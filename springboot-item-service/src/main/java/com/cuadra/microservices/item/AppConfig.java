package com.cuadra.microservices.item;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

	/*
	 * RestTemplate: nos sirve como cliente HTTP
	 */
	@Bean
	@LoadBalanced
	public RestTemplate registerRestTempleate() {
		return new RestTemplate();
	}
	
}
