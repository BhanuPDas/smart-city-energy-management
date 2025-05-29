package com.smartcity.energy_optimization_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories
@EntityScan
public class EnergyOptimizationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnergyOptimizationServiceApplication.class, args);
    }
}
