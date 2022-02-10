package br.com.lucasilva.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

import java.util.Locale;
import java.util.function.Consumer;

@OpenAPIDefinition(info =
@Info(title = "Book service API",
        version = "v1",
        description = "Documentation of Book Service API"))
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {

        var license = new License()
                .name("Apache 2.0")
                .url("http://springdoc.org");

        var info = new io.swagger.v3.oas.models.info.Info()
                .title("Book Service API")
                .version("v1")
                .license(license);

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
