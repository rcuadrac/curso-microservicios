package com.cuadra.microservices.product.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.cuadra.microservices.commons.model.entity.Product;


public interface ProductDao extends CrudRepository<Product, Long>{

}
