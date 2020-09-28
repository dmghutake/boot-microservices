package com.dmg.boot.microservice.currencyexchangeservice.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dmg.boot.microservice.currencyexchangeservice.model.ExchangeValue;
import com.dmg.boot.microservice.currencyexchangeservice.repository.ExchangeValueRepository;

@RestController
public class CurrencyExchangeResource{
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Environment environment;
	
	@Autowired
	ExchangeValueRepository exchangeValueRepository;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveCurrencyExchangeValue(@PathVariable String from,@PathVariable String to ) {
		ExchangeValue xchangeValue= exchangeValueRepository.findByFromAndTo(from, to);
		logger.info("local server.port-->>{}",environment.getProperty("local.server.port"));
		logger.info("server.port-->>{}",environment.getProperty("server.port"));
		logger.info("server.port Integer -->>{}",Integer.parseInt(environment.getProperty("local.server.port")));
		logger.info("xchangeValue-->>{}",xchangeValue);
		xchangeValue.setPort(Integer.valueOf(environment.getProperty("local.server.port")));
		
		logger.info("xchangeValue-->>{}",xchangeValue);
		return xchangeValue;
	}
}
