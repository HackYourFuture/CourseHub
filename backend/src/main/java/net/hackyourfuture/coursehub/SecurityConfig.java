package net.hackyourfuture.coursehub;

import net.hackyourfuture.coursehub.security.ApiKeyAuthenticationFilter;
import net.hackyourfuture.coursehub.service.UserAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserAuthenticationService userAuthenticationService) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    var config = new CorsConfiguration();
                    config.addAllowedOriginPattern("http://localhost");
                    config.addAllowedOriginPattern("http://localhost:*");
                    config.addAllowedOriginPattern("https://coursehub.hyf.dev");
                    config.addAllowedHeader("*");
                    config.addAllowedMethod("*");
                    config.setAllowCredentials(true);
                    return config;
                }))
                // Disable the default logout endpoint as we implemented our own
                .logout(AbstractHttpConfigurer::disable)
                // Disable the default login (form) endpoint as we implemented our own
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // allow CORS pre-flight requests to any endpoint
                        .requestMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll()
                        // this is an internal mapping to display errors
                        // allowing it without authentication so that errors are displayed correctly
                        .requestMatchers("/error")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/login", "/logout", "/register")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/courses", "/swagger-ui/**", "/v3/api-docs/**")
                        .permitAll()
                        .requestMatchers("/students/**")
                        .hasRole("student")
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(apiKeyAuthenticationFilter(userAuthenticationService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public ApiKeyAuthenticationFilter apiKeyAuthenticationFilter(UserAuthenticationService userAuthenticationService) {
        return new ApiKeyAuthenticationFilter(userAuthenticationService);
    }
}
