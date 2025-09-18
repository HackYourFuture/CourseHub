package net.hackyourfuture.coursehub;

import org.springdoc.core.customizers.ServerBaseUrlCustomizer;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.providers.SpringWebProvider;
import org.springdoc.webmvc.ui.SwaggerWelcomeWebMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This configuration adjusts Swagger URLs when the application is running behind a reverse proxy on Digital Ocean.
 */
@Configuration
public class SwaggerBehindReverseProxyConfig {

    @Bean
    SwaggerWelcomeWebMvc swaggerWelcome(SwaggerUiConfigProperties swaggerUiConfig, SpringDocConfigProperties springDocConfigProperties, SpringWebProvider springWebProvider) {
        if (isBehindDigitalOceanReverseProxy()) {
            swaggerUiConfig.setUrl("/api/v3/api-docs");
            swaggerUiConfig.setConfigUrl("/api/v3/api-docs/swagger-config");
        }
        return new SwaggerWelcomeWebMvc(swaggerUiConfig, springDocConfigProperties, springWebProvider);
    }

    @Bean
    ServerBaseUrlCustomizer serverBaseUrlCustomizer() {
        return (serverBaseUrl, request) -> {
            if (isBehindDigitalOceanReverseProxy()) {
                return serverBaseUrl + "/api";
            }
            return serverBaseUrl;
        };
    }

    private boolean isBehindDigitalOceanReverseProxy() {
        var appDomain = System.getenv("APP_DOMAIN");
        return appDomain != null && appDomain.contains("coursehub.hyf.dev");
    }
}
