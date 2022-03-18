package br.com.lucasilva.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasilva.model.Cambio;
import br.com.lucasilva.repository.CambioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cambio Service API")
@RestController
@RequestMapping("cambio-service")
public class CambioController {

	private Logger log = LoggerFactory.getLogger(CambioController.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CambioRepository cambioRepository;
	
	@Operation(description = "Get cambio from currency")
	@GetMapping(value = "/{amount}/{from}/{to}")
	public Cambio getCambio(
			@PathVariable("amount") BigDecimal amount,
			@PathVariable("from") String from,
			@PathVariable("to") String to) {

		log.info("getCambio is called with -> {}, {} and {}", amount, from, to);

		var cambio = cambioRepository.findByFromAndTo(from, to)
				.orElseThrow(() -> new RuntimeException("Currency unsupported!"));

		var port = environment.getProperty("local.server.port");
		cambio.setEnvironment(port);

		var convertedValue = cambio.getConversionFactor().multiply(amount);
		cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));

		return cambio;
	}
}