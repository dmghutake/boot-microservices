package com.dmg.boot.microservice.currencyexchangeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dmg.boot.microservice.currencyexchangeservice.model.ExchangeValue;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long>{
	
	ExchangeValue findByFromAndTo(String from,String to);

}
