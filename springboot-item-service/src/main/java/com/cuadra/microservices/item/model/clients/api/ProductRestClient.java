package com.cuadra.microservices.item.model.clients.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cuadra.microservices.commons.model.entity.Product;


@FeignClient(name = "product-service")
public interface ProductRestClient {

	@GetMapping("/")
	List<Product> getAll();
	
	@GetMapping("/{id}")
	Product findById(@PathVariable("id") Long id);
	
}
