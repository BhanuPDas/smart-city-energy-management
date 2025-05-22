package de.fhdo.city_mgmt_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories
@EntityScan
@EnableCaching
public class CityManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityManagementServiceApplication.class, args);
	}

}
