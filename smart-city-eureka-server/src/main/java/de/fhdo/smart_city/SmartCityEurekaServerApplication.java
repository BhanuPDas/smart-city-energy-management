package de.fhdo.smart_city;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("de.fhdo.smart_city")
@EnableEurekaServer
@SpringBootApplication
public class SmartCityEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartCityEurekaServerApplication.class, args);
	}

}
