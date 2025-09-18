package net.hackyourfuture.coursehub;

import org.springdoc.core.customizers.ServerBaseUrlCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This configuration adjusts Swagger URLs when the application is running behind a reverse proxy on Digital Ocean.
 */
@Configuration
public class SwaggerBehindReverseProxyConfig {

    @Bean
    ServerBaseUrlCustomizer serverBaseUrlCustomizer() {
        return (serverBaseUrl, request) -> {
            if (serverBaseUrl.contains("coursehub.hyf.dev")) {
                return serverBaseUrl + "/api";
            }
            return serverBaseUrl;
        };
    }
}
