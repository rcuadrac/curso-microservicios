package com.cuadra.microservices.item.model.service.api;

import java.util.List;

import com.cuadra.microservices.commons.model.entity.Product;
import com.cuadra.microservices.item.model.Item;

public interface ItemService {
	
	List<Item> getAll();
	Item findById(Long id, Integer quantity);
	Product save(Product product);
	Product update(Product product, Long id);
	void delete(Long id);

}
