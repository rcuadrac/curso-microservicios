package com.cuadra.microservices.product.model.service.api;

import java.util.List;

import com.cuadra.microservices.commons.model.entity.Product;


public interface ProductService {
	
	List<Product> findAll();
	Product findById(Long id);
	Product save(Product product);
	void deleteById(Long id);

}
