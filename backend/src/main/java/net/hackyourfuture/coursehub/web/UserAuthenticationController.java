package net.hackyourfuture.coursehub.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.hackyourfuture.coursehub.service.UserAuthenticationService;
import net.hackyourfuture.coursehub.web.model.ApiKeyResponse;
import net.hackyourfuture.coursehub.web.model.HttpErrorResponse;
import net.hackyourfuture.coursehub.web.model.LoginRequest;
import net.hackyourfuture.coursehub.web.model.LoginSuccessResponse;
import net.hackyourfuture.coursehub.web.model.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated // will make sure that every request body annotated with @RequestBody is validated
@RestController
public class UserAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserAuthenticationService userAuthenticationService;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public UserAuthenticationController(
            AuthenticationManager authenticationManager,
            UserAuthenticationService userAuthenticationService) {
        this.authenticationManager = authenticationManager;
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @RequestBody LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        try {
            var response = authenticate(httpRequest, httpResponse, request.emailAddress(), request.password());
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            if (e instanceof BadCredentialsException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new HttpErrorResponse("Invalid credentials provided"));
            }
            if (e instanceof AuthenticationCredentialsNotFoundException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new HttpErrorResponse("No user found for provided email address"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new HttpErrorResponse("Something went wrong"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpRequest) {
        SecurityContextHolder.clearContext();
        var session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public LoginSuccessResponse register(@RequestBody RegisterRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        userAuthenticationService.register(
                request.firstName(),
                request.lastName(),
                request.emailAddress(),
                request.password()
        );
        // Authenticate the user and return the response
        return authenticate(httpRequest, httpResponse, request.emailAddress(), request.password());
    }

    @PostMapping("/generate-api-key")
    public ResponseEntity<Object> generateApiKey() {
        try {
            String apiKey = userAuthenticationService.generateApiKey();
            return ResponseEntity.ok(new ApiKeyResponse(apiKey));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new HttpErrorResponse("Unable to generate API key"));
        }
    }

    private LoginSuccessResponse authenticate(HttpServletRequest request, HttpServletResponse response, String email, String password) {
        // Authenticate the user with the provided credentials (email and password)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.clearContext();
        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        // Save the authenticated user in the Spring security context
        securityContextRepository.saveContext(context, request, response);

        // Retrieve the corresponding user data to return in a login response
        var authenticatedUser = userAuthenticationService.currentAuthenticatedUser();
        return new LoginSuccessResponse(
                authenticatedUser.getUserId(),
                authenticatedUser.getFirstName(),
                authenticatedUser.getLastName(),
                authenticatedUser.getEmailAddress(),
                authenticatedUser.getRole()
        );
    }
}
