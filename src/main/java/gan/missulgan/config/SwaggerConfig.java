package gan.missulgan.config;

import gan.missulgan.security.auth.AuthDTO;
import gan.missulgan.security.auth.AuthenticatedEmail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

import java.util.List;

@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

    private static final String API_TITLE = "Missulgan API";
    private static final String API_DESCRIPTION = "Missulgan API Document";
    private static final String API_VERSION = "Version 1";
    private static final String SWAGGER_PATH = "/swagger-ui/index.html";
    private static final String API_KEY_NAME = "JWT AccessToken";
    private static final String SCOPE_GLOBAL = "global";
    private static final String SCOPE_GLOBAL_DESCRIPTION = "to access resources";

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .version(API_VERSION)
                .build();
    }

    @Bean
    public UiConfiguration uiConfiguration() {
        return UiConfigurationBuilder.builder()
                .docExpansion(DocExpansion.LIST)
                .build();
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(getApiInfo())
                .ignoredParameterTypes(AuthenticatedEmail.class, AuthDTO.class)
                .securityContexts(List.of(getSecurityContext()))
                .securitySchemes(List.of(getScheme()))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/docs", SWAGGER_PATH);
        registry.addRedirectViewController("/swagger-ui", SWAGGER_PATH);
    }

    private SecurityScheme getScheme() {
        return HttpAuthenticationScheme
                .JWT_BEARER_BUILDER
                .name(API_KEY_NAME)
                .description("`eyJhbGciOiJIUzUxMiJ9.xxxxxxxxxxxx` 입력.  <br><br>(**`Bearer` 필요없음**)")
                .build();
    }

    private SecurityContext getSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(getSecurityReferences())
                .build();
    }

    private List<SecurityReference> getSecurityReferences() {
        return List.of(new SecurityReference(API_KEY_NAME, getAuthorizationScopes()));
    }

    private AuthorizationScope[] getAuthorizationScopes() {
        AuthorizationScope globalScope = new AuthorizationScopeBuilder()
                .scope(SCOPE_GLOBAL)
                .description(SCOPE_GLOBAL_DESCRIPTION)
                .build();
        return new AuthorizationScope[]{globalScope};
    }
}
