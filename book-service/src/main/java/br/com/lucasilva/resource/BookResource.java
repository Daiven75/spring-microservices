package br.com.lucasilva.resource;

import br.com.lucasilva.integration.Cambio;
import br.com.lucasilva.integration.CambioIntegration;
import br.com.lucasilva.model.Book;
import br.com.lucasilva.repository.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Tag(name = "Book endpoint")
@RestController
@RequestMapping("book-service")
public class BookResource {

    @Autowired
    private Environment environment;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private WebClient webClient;

    @Autowired
    private CambioIntegration cambioIntegration;

    @Operation(summary = "Find a specific book by your ID")
    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency) {

        var book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found!"));

//        var cambioWebClient = Objects.requireNonNull(webClient
//                        .get()
//                        .uri("{amount}/{from}/{to}", book.getPrice(), "USD", currency)
//                        .retrieve()
//                        .bodyToMono(Cambio.class)
//                        .block());
//        var conversionValueWebClient = cambioWebClient.getConversionValue();

        var cambio = cambioIntegration.getCambio(book.getPrice(), 2.0, currency)
                .orElseThrow(() -> new RuntimeException("cambio failure!"));
        var conversionValue = cambio.getConversionValue();
        book.setPrice(conversionValue);

        var port = environment.getProperty("local.server.port");
        book.setEnvironment("book-service = " + port
                + " | cambio-service = " + cambio.getEnvironment());

        return book;
    }
}
