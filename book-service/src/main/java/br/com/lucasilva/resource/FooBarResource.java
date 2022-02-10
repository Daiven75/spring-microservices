package br.com.lucasilva.resource;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Foo bar")
@RestController
@RequestMapping("book-service")
public class FooBarResource {

    private final Logger log = LoggerFactory.getLogger(FooBarResource.class);

    // test operations with resilience4j
    @Operation(summary = "Foo bar")
    @GetMapping("foo-bar")
    @Retry(name = "foo-bar", fallbackMethod = "fallbackMethod")
    @RateLimiter(name = "default")
    public String fooBar() {
        log.info("Request to foo-bar is received!");
//        var response = new RestTemplate()
//                .getForEntity("http://localhost:8089/foo-bar", String.class);
//        return response.getBody();
        return "Foo-Bar method!!!";
    }

    private String fallbackMethod(Exception ex) {
        return "fallbackMethod foo-bar!!!";
    }
}
