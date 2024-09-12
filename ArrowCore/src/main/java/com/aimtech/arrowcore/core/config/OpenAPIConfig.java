package com.aimtech.arrowcore.core.config;

import com.aimtech.arrowcore.domain.business.dto.responses.errors.CustomErrorResponse;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@SuppressWarnings("rawtypes")
public class OpenAPIConfig {

    private final MessageSource messageSource;
    private final Content errorResponse = new Content();

    public static final String SECURITY_SCHEME = "bearerAuth";
    private static final String LICENSE = "Apache 2.0";
    private static final String LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0";
    private static final List<String> SUPPORTED_LANGUAGES = List.of("pt-BR", "en-US", "es");

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("arrow-core-internal")
                .addOpenApiCustomizer(this::customizeInternalApi)
                .addOpenApiCustomizer(exceptionResponseCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi groupedOpenApiPublic() {
        return GroupedOpenApi.builder()
                .group("arrow-core-public")
                .pathsToMatch("/public/**")
                .addOpenApiCustomizer(this::customizePublicApi)
                .addOpenApiCustomizer(exceptionResponseCustomizer())
                .build();
    }

    @Bean
    public OpenApiCustomizer exceptionResponseCustomizer() {
        return openApi -> {
            Map<String, Schema> schemas = generateSchema();
            schemas.forEach(openApi.getComponents().getSchemas()::putIfAbsent);
        };
    }

    private void customizeInternalApi(OpenAPI openApi) {
        openApi
                .info(new Info()
                        .title("ARROW")
                        .version("v1")
                        .description("Software para gerenciamento de pequenas e médias empresas")
                        .license(new License().name(LICENSE).url(LICENSE_URL)))
                .externalDocs(new ExternalDocumentation().description("Postman Collection").url("https://www.google.com.br/"))
                .tags(generateTags())
                .components(openApi.getComponents()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .name(SECURITY_SCHEME))
                );
    }

    private void customizePublicApi(OpenAPI openApi) {
        openApi
                .info(new Info()
                        .title("Sistema de gestão para pequenas e grandes empresas")
                        .version("v1")
                        .description("API destinada para ser consumida em clientes.")
                        .license(new License().name(LICENSE).url(LICENSE_URL)))
                .externalDocs(new ExternalDocumentation().description("Postman Collection").url("https://google.com.br"));
    }

    private List<Tag> generateTags() {
        Locale locale = LocaleContextHolder.getLocale();

        return List.of(
                new Tag()
                        .name("Auth")
                        .description(messageSource.getMessage(
                                "arrowcore.openapi.tags.description.auth",
                                null,
                                locale
                        )),

                new Tag()
                        .name("Admin | User")
                        .description(messageSource.getMessage(
                                "arrowcore.openapi.tags.description.admin.UserController",
                                null,
                                locale
                        )),

                new Tag()
                        .name("Management | City")
                        .description(messageSource.getMessage(
                                "arrowcore.openapi.tags.description.management.addresses.CityController",
                                null,
                                locale
                        )),

                new Tag()
                        .name("Management | Country")
                        .description(messageSource.getMessage(
                                "arrowcore.openapi.tags.description.management.addresses.CountryController",
                                null,
                                locale
                        )),

                new Tag()
                        .name("Management | State")
                        .description(messageSource.getMessage(
                                "arrowcore.openapi.tags.description.management.addresses.StateController",
                                null,
                                locale
                        )),

                new Tag()
                        .name("Management | StreetType")
                        .description(messageSource.getMessage(
                                "arrowcore.openapi.tags.description.management.addresses.StreetTypeController",
                                null,
                                locale
                        )),

                new Tag()
                        .name("Management | Company")
                        .description(messageSource.getMessage(
                                "arrowcore.openapi.tags.description.management.CompanyController",
                                null,
                                locale
                        )),

                new Tag()
                        .name("Management | CompanyRepresentatives")
                        .description(messageSource.getMessage(
                                "arrowcore.openapi.tags.description.management.CompanyRepresentativesController",
                                null,
                                locale
                        ))
        );
    }

    private Schema<String> createLanguageSchema() {
        return new Schema<String>()
                .type("string")
                ._enum(SUPPORTED_LANGUAGES);
    }

    private void generateHttpResponseStatus(PathItem pathItem) {
        pathItem.readOperationsMap().forEach(this::generateHttpResponseStatus);
    }

    @SuppressWarnings("DuplicateBranchesInSwitch")
    private void generateHttpResponseStatus(PathItem.HttpMethod httpMethod, Operation operation) {
        ApiResponses responses = operation.getResponses();
        Locale locale = LocaleContextHolder.getLocale();

        responses.addApiResponse("401", createApiResponse("arrowcore.http.responses.unauthorized", locale));
        responses.addApiResponse("403", createApiResponse("arrowcore.http.responses.forbidden", locale));
        responses.addApiResponse("406", createApiResponse("arrowcore.http.responses.notAcceptable", locale));
        switch (httpMethod) {
            case GET:
                addCommonApiResponses(responses, locale);
                responses.addApiResponse("404", createApiResponse("arrowcore.http.responses.notFound", locale));
                break;
            case POST:
                addCommonApiResponses(responses, locale);
                responses.addApiResponse("422", createApiResponse("arrowcore.http.responses.unprocessableEntity", locale));
                responses.addApiResponse("404", createApiResponse("arrowcore.http.responses.notFound", locale));
                break;
            case PUT:
                addCommonApiResponses(responses, locale);
                responses.addApiResponse("404", createApiResponse("arrowcore.http.responses.notFound", locale));
                break;
            case PATCH:
                addCommonApiResponses(responses, locale);
                responses.addApiResponse("404", createApiResponse("arrowcore.http.responses.notFound", locale));
                break;
            case DELETE:
                addCommonApiResponses(responses, locale);
                responses.addApiResponse("404", createApiResponse("arrowcore.http.responses.notFound", locale));
                break;
        }
    }

    private void addCommonApiResponses(ApiResponses responses, Locale locale) {
        responses.addApiResponse("400", createApiResponse("arrowcore.http.responses.badRequest", locale));
    }

    private ApiResponse createApiResponse(String messageCode, Locale locale) {
        return new ApiResponse()
                .description(messageSource.getMessage(messageCode, null, locale))
                .content(errorResponse);
    }

    private Map<String, Schema> generateSchema() {
        errorResponse.addMediaType("application/json",
                new MediaType().schema(new Schema().$ref("CustomErrorResponse")));

        Map<String, Schema> exceptionSchema = ModelConverters.getInstance().read(CustomErrorResponse.class);

        return new HashMap<>(exceptionSchema);
    }
}