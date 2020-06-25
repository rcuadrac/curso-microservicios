package com.cuadra.microservices.item.model.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cuadra.microservices.commons.model.entity.Product;
import com.cuadra.microservices.item.model.Item;
import com.cuadra.microservices.item.model.service.api.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@RefreshScope
@RestController
@Slf4j
public class ItemController {

	private ItemService itemService;
	
	@Autowired
	private Environment env;
	
	@Value("${configuracion.texto}")
	private String text;
	
	@Autowired
	public ItemController(@Qualifier("restTemplateService") final ItemService itemService) {
		this.itemService = itemService;
	}
	
	
	@GetMapping("/")
	public List<Item> getAll() {
		return itemService.getAll();
	}
	
	@HystrixCommand(fallbackMethod = "alternMethod")
	@GetMapping("/{id}/quantity/{quantity}")
	public Item getById(@PathVariable("id") Long id, @PathVariable Integer quantity) {
		return itemService.findById(id, quantity);
	}
	
	public Item alternMethod(Long id, Integer quantity) {
		
		Item defaultItem = new Item();
		defaultItem.setQuantity(1);
		Product product = new Product();
		product.setName("Boss Katana");
		product.setPrice(199.99);
		product.setId(id);
		defaultItem.setProduct(product);
		
		return defaultItem;
	}
	
	@GetMapping("/get-config")
	public ResponseEntity<?> getConfig(@Value("${server.port}") String port) {
		log.info(text);
		Map<String, String> json = new HashMap<>();
		json.put("text", text);
		json.put("port", port);
		
		if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
			json.put("autor.email", env.getProperty("configuracion.autor.email"));
		}
		
		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK); 
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Product create(@RequestBody Product product) {
		return itemService.save(product);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Product update(@RequestBody Product product, @PathVariable Long id) {
		return itemService.update(product, id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		itemService.delete(id);
	}
	
}
