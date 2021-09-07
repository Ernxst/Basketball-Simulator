package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ServerBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Configuration
public class OpenApiDocsConfig {
    private static final String DEFAULT_BASE_PATH = "/api/v1";

    private ApiInfo getApiInfo() {
        Contact contact = new Contact("Ernest Badu", "https://basketball-simulator-4f6a7.web.app", "ernzst@gmail.com");
        return new ApiInfoBuilder()
                .title("Basketball Simulator API")
                .description("OpenAPI specification for the backend.")
                .version("1.0")
                .contact(contact)
                .license("MIT")
                .build();
    }

    private SecurityScheme jwtScheme() {
        return HttpAuthenticationScheme.JWT_BEARER_BUILDER
                .name("JWT")
                .build();
    }

    private SecurityContext securityContext() {
        String regex = "^((?!users/login|users/register).)*$";
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .operationSelector(o -> o.requestMappingPattern().matches(regex))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        SecurityReference reference = SecurityReference.builder()
                .scopes(new AuthorizationScope[0])
                .reference("JWT")
                .build();
        return Collections.singletonList(reference);
    }

    private List<SecurityScheme> securitySchemes() {
        return Collections.singletonList(jwtScheme());
    }

    private Server getServer() {
        return new ServerBuilder()
                .name("API")
                .description("The location of the REST API")
                .url("https://basketball-simulator-web.herokuapp.com")
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(getApiInfo())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(securitySchemes())
                .servers(getServer())
                .enableUrlTemplating(true)
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .pathMapping(DEFAULT_BASE_PATH)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicate.not(PathSelectors.regex("/(error|actuator).*")))
                .build();
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .showCommonExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }
}
