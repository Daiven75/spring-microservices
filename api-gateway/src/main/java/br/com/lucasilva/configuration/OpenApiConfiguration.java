package br.com.lucasilva.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    @Lazy(false)
    public List<GroupedOpenApi> apisList(
            SwaggerUiConfigParameters swaggerConfig,
            RouteDefinitionLocator locator) {

        var definitions = locator.getRouteDefinitions()
                .collectList()
                .block();

        // group all microservices inside swagger ui api-gateway
        definitions.stream().filter(routeDefinition -> routeDefinition.getId()
                .matches(".*-service"))
                .forEach(rd -> {
                    String name = rd.getId();
                    swaggerConfig.addGroup(name);
                    GroupedOpenApi.builder()
                            .pathsToMatch("/" + name + "/**")
                            .group(name)
                            .build();
                });
        return new ArrayList<>();
    }
}
