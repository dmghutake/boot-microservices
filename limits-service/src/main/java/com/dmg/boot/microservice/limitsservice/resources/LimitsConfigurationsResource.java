package com.dmg.boot.microservice.limitsservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dmg.boot.microservice.limitsservice.Configuration;
import com.dmg.boot.microservice.limitsservice.model.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;

@RestController
public class LimitsConfigurationsResource {

	@Autowired
	private Configuration configuration;

	@GetMapping("/limits")
	LimitConfiguration retrieveLimitsFromConfigurations() {
		return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum());
	}

	@GetMapping("/fault-tolerance-limits")
	@HystrixCommand(fallbackMethod = "fallbackretrieveLimitsFromConfigurations")
	LimitConfiguration retrieveConfigurations() {
		throw new RuntimeException("Not Available");
	}

	LimitConfiguration fallbackretrieveLimitsFromConfigurations() {
		return new LimitConfiguration(999, 9);
	}
}
