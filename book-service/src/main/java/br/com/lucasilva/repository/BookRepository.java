package br.com.lucasilva.repository;

import br.com.lucasilva.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> { }
