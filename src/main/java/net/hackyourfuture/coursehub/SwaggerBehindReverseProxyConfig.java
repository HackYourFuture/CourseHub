package net.hackyourfuture.coursehub;

import org.springdoc.core.customizers.ServerBaseUrlCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
