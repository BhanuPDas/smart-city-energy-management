package de.fhdo.city_mgmt_service.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class CityMgmtConfig {

	@Bean(autowireCandidate = true)
	@Scope("singleton")
	ObjectMapper createObjectMapper() {
		ObjectMapper obj = new ObjectMapper();
		obj.registerModule(new JavaTimeModule());
		return obj;
	}

	@Bean(autowireCandidate = true)
	@LoadBalanced
	RestTemplate createRestTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
