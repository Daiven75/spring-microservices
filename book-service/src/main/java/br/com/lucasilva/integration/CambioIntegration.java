package br.com.lucasilva.integration;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "cambio-service")
public interface CambioIntegration {

    @Retry(name = "cambio-service")
    @GetMapping("cambio-service/{amount}/{from}/{to}")
    Optional<Cambio> getCambio(
            @PathVariable("amount") Double amount,
            @PathVariable("from") Double from,
            @PathVariable("to") String to);
}
