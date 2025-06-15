package de.fh.energymanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EnergyManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnergyManagementServiceApplication.class, args);
    }

}
