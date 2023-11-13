package com.sks.currencyconversionservice.controllers;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sks.currencyconversionservice.clients.CurrencyExchangeProxy;
import com.sks.currencyconversionservice.models.CurrencyConversion;

@RestController
public class CurrencyConversionController {
	
	private Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);
	
	@Autowired
	private CurrencyExchangeProxy cep ;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		//CHANGE-KUBERNETES
		logger.info("calculateCurrencyConversion called with {} to {} with {}", from, to, quantity);
		
//		HashMap<String,String> uriVars = new HashMap();
//		uriVars.put("from","USD");
//		uriVars.put("to", "INR");
//		ResponseEntity<CurrencyConversion> re = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
//				CurrencyConversion.class,uriVars);
//		
//		CurrencyConversion cc = re.getBody();
//		cc.setQuantity(quantity);
//		cc.setTotalCalculatedAmount(quantity.multiply(cc.getConversionMultiple()));
//		
		CurrencyConversion cc = cep.retrieveExchangeValue(from, to);
		cc.setQuantity(quantity);
		cc.setTotalCalculatedAmount(quantity.multiply(cc.getConversionMultiple()));
		
		return cc;
		//return new CurrencyConversion(10001L, from, to, quantity, BigDecimal.ONE, BigDecimal.ONE, "");

	}

}
