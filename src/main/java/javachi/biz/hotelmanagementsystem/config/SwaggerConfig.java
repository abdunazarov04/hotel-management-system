package javachi.biz.hotelmanagementsystem.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicAuthUserAPI() {
        return GroupedOpenApi.builder()
                .group("Auth User")
                .pathsToMatch("/auth/**")
                .build();
    }
    @Bean
    public GroupedOpenApi userAPI() {
        return GroupedOpenApi.builder()
                .group("User")
                .pathsToMatch("/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi hotelAPI() {
        return GroupedOpenApi.builder()
                .group("Hotel")
                .pathsToMatch("/hotel/**")
                .build();
    }

    @Bean
    public GroupedOpenApi roomAPI() {
        return GroupedOpenApi.builder()
                .group("Room")
                .pathsToMatch("/room/**")
                .build();
    }

    @Bean
    public GroupedOpenApi orderAPI() {
        return GroupedOpenApi.builder()
                .group("Order")
                .pathsToMatch("/order/**")
                .build();
    }

    @Bean
    public GroupedOpenApi paymentAPI() {
        return GroupedOpenApi.builder()
                .group("Payment")
                .pathsToMatch("/payment/**")
                .build();
    }
    @Bean
    public GroupedOpenApi commentAPI() {
        return GroupedOpenApi.builder()
                .group("Comments")
                .pathsToMatch("/comment/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminAPI() {
        return GroupedOpenApi.builder()
                .group("Admin")
                .pathsToMatch("/admin/**")
                .build();
    }


    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

}
