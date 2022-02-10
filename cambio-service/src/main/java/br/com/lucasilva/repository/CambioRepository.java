package br.com.lucasilva.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lucasilva.model.Cambio;

import java.util.Optional;

public interface CambioRepository extends JpaRepository<Cambio, Long> {

	Optional<Cambio> findByFromAndTo(String from, String to);
}