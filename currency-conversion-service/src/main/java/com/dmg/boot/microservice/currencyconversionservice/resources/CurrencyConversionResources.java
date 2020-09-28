package com.dmg.boot.microservice.currencyconversionservice.resources;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dmg.boot.microservice.currencyconversionservice.model.CurrencyConversionBean;
import com.dmg.boot.microservice.currencyconversionservice.proxy.CurrencyExchangeServiceProxy;

@RestController
public class CurrencyConversionResources {
	Logger logger =LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CurrencyExchangeServiceProxy proxy;

	@GetMapping("/currency-convertor/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean converCurrency(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity) {
		
		Map<String,String> uriVariables= new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversionBean> responseEntity=
				new RestTemplate().getForEntity("http://localhost:8001/currency-exchange/from/EUR/to/INR", 
						CurrencyConversionBean.class, uriVariables);
		CurrencyConversionBean response = responseEntity.getBody();
		
		return new CurrencyConversionBean(
				response.getId(),from,to,response.getConversionMultiple(),
				quantity,quantity.multiply(response.getConversionMultiple()),response.getPort());
	}
	
	@GetMapping("/currency-convertor-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean converCurrencyFeign(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity) {
		CurrencyConversionBean response = proxy.retrieveCurrencyExchangeValue(from, to);
		logger.info("response--> {}",response);
		return new CurrencyConversionBean(
				response.getId(),from,to,response.getConversionMultiple(),
				quantity,quantity.multiply(response.getConversionMultiple()),response.getPort());
	}	
}
