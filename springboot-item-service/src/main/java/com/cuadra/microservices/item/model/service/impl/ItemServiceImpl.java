package com.cuadra.microservices.item.model.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cuadra.microservices.commons.model.entity.Product;
import com.cuadra.microservices.item.model.Item;
import com.cuadra.microservices.item.model.service.api.ItemService;

import lombok.extern.log4j.Log4j2;

@Service("restTemplateService")
@Log4j2
public class ItemServiceImpl implements ItemService {

	private static final String BASE_PATH = "http://product-service/";
	private static final String FIND_BY_ID = "/{id}";
	
	@Autowired
	private RestTemplate restClient;
	
	@Override
	public List<Item> getAll() {
		log.info("::: using restTemplateService ::::");
		List<Product> products = Arrays.asList(restClient.getForObject(
				BASE_PATH, Product[].class));
		return products.stream().map(product -> 
			new Item(product, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer quantity) {
		Map<String, String> pathVariables = new HashMap<>();
		pathVariables.put("id", id.toString());
		Product product = restClient.getForObject(BASE_PATH + FIND_BY_ID, 
				Product.class, pathVariables);
		
		return new Item(product, quantity);
	}

	@Override
	public Product save(Product product) {
		
		HttpEntity<Product> body = new HttpEntity<>(product);
		ResponseEntity<Product> response = restClient.
				exchange(BASE_PATH, HttpMethod.POST, body, Product.class);
		Product productResponse = response.getBody();
		
		return productResponse;
	}

	@Override
	public Product update(Product product, Long id) {
		
		Map<String, String> pathVariables = new HashMap<>();
		pathVariables.put("id", id.toString());
		HttpEntity<Product> body = new HttpEntity<>(product);
		ResponseEntity<Product> response = restClient.
				exchange(BASE_PATH.concat(FIND_BY_ID), HttpMethod.PUT, body, Product.class, pathVariables);
		
		
		return response.getBody();
	}

	@Override
	public void delete(Long id) {
		Map<String, String> pathVariables = new HashMap<>();
		pathVariables.put("id", id.toString());
		restClient.delete(BASE_PATH.concat(FIND_BY_ID), pathVariables);
	}

}
