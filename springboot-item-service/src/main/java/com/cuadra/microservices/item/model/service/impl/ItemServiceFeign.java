package com.cuadra.microservices.item.model.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cuadra.microservices.commons.model.entity.Product;
import com.cuadra.microservices.item.model.Item;
import com.cuadra.microservices.item.model.clients.api.ProductRestClient;
import com.cuadra.microservices.item.model.service.api.ItemService;

import lombok.extern.log4j.Log4j2;

@Service
@Qualifier("item-service-feign")
@Log4j2
public class ItemServiceFeign implements ItemService {
	
	private ProductRestClient productRestClient;
	
	@Autowired
	public ItemServiceFeign(final ProductRestClient productRestClient) {
		this.productRestClient = productRestClient;
	}

	@Override
	public List<Item> getAll() {
		log.info("::::: Using feign ::::: ");
		return productRestClient.getAll()
				.stream().map(p -> new Item(p, 1))
				.collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer quantity) {
		return new Item(productRestClient.findById(id), quantity);
	}

	@Override
	public Product save(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product update(Product product, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
