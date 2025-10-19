package net.hackyourfuture.coursehub.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.hackyourfuture.coursehub.data.AuthenticatedUser;
import net.hackyourfuture.coursehub.service.UserAuthenticationService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final UserAuthenticationService userAuthenticationService;
    private static final String API_KEY_HEADER = "X-API-Key";

    public ApiKeyAuthenticationFilter(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Skip API key authentication if already authenticated
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader(API_KEY_HEADER);

        if (apiKey != null && !apiKey.isEmpty()) {
            // Look up user by API key
            AuthenticatedUser user = userAuthenticationService.findUserByApiKey(apiKey);

            if (user != null) {
                // Create an authentication token
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null, // No credentials needed as we authenticated via API key
                                user.getAuthorities()
                        );

                // Set authentication in context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
