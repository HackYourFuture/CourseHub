package net.hackyourfuture.coursehub;

import org.springdoc.core.customizers.ServerBaseUrlCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This configuration adjusts Swagger URLs when the application is running behind a reverse proxy on Digital Ocean.
 */
@Configuration
public class SwaggerBehindReverseProxyConfig {

    @Value("${open-api.suffix:#{environment.OPEN_API_SUFFIX}}")
    private String openApiSuffix;

    @Bean
    ServerBaseUrlCustomizer serverBaseUrlCustomizer() {
        return (serverBaseUrl, request) -> serverBaseUrl + openApiSuffix;
    }
}
