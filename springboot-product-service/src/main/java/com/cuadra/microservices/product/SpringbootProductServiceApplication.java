
package com.cuadra.microservices.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.cuadra.microservices.commons.model.entity"})
public class SpringbootProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootProductServiceApplication.class, args);
	}

}
