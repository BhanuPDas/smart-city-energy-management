package de.fhdo.city_mgmt_service.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

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

	@Bean(autowireCandidate = true)
	Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> {
			factory.configure(builder -> builder
					.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(5)).build())
					.circuitBreakerConfig(CircuitBreakerConfig.custom().failureRateThreshold(50).minimumNumberOfCalls(5)
							.slidingWindowSize(10).waitDurationInOpenState(Duration.ofSeconds(5)).build()),
					"register");
			factory.configure(builder -> builder
					.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(5)).build())
					.circuitBreakerConfig(CircuitBreakerConfig.custom().failureRateThreshold(50).minimumNumberOfCalls(5)
							.slidingWindowSize(10).waitDurationInOpenState(Duration.ofSeconds(5)).build()),
					"login");
			factory.configure(builder -> builder
					.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(5)).build())
					.circuitBreakerConfig(CircuitBreakerConfig.custom().failureRateThreshold(50).minimumNumberOfCalls(5)
							.slidingWindowSize(10).waitDurationInOpenState(Duration.ofSeconds(5)).build()),
					"energyRecommend");
			factory.configure(builder -> builder
					.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(5)).build())
					.circuitBreakerConfig(CircuitBreakerConfig.custom().failureRateThreshold(50).minimumNumberOfCalls(5)
							.slidingWindowSize(10).waitDurationInOpenState(Duration.ofSeconds(5)).build()),
					"buildingRegister");
			factory.configure(builder -> builder
					.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(5)).build())
					.circuitBreakerConfig(CircuitBreakerConfig.custom().failureRateThreshold(50).minimumNumberOfCalls(5)
							.slidingWindowSize(10).waitDurationInOpenState(Duration.ofSeconds(5)).build()),
					"buildingUpdate");
			factory.configure(builder -> builder
					.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(5)).build())
					.circuitBreakerConfig(CircuitBreakerConfig.custom().failureRateThreshold(50).minimumNumberOfCalls(5)
							.slidingWindowSize(10).waitDurationInOpenState(Duration.ofSeconds(5)).build()),
					"energyRegister");
			factory.configure(builder -> builder
					.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(5)).build())
					.circuitBreakerConfig(CircuitBreakerConfig.custom().failureRateThreshold(50).minimumNumberOfCalls(5)
							.slidingWindowSize(10).waitDurationInOpenState(Duration.ofSeconds(5)).build()),
					"energyUpdate");
			factory.configure(builder -> builder
					.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(5)).build())
					.circuitBreakerConfig(CircuitBreakerConfig.custom().failureRateThreshold(50).minimumNumberOfCalls(5)
							.slidingWindowSize(10).waitDurationInOpenState(Duration.ofSeconds(5)).build()),
					"energyAnalytics");
		};
	}

}
