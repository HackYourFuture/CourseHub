package net.hackyourfuture.coursehub.web;

import jakarta.servlet.http.HttpServletRequest;
import net.hackyourfuture.coursehub.repository.StudentRepository;
import net.hackyourfuture.coursehub.service.UserAuthenticationService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated // will make sure that every request body annotated with @RequestBody is validated
@RestController
public class UserAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserAuthenticationService userAuthenticationService;
    private final StudentRepository studentRepository;

    public UserAuthenticationController(
            AuthenticationManager authenticationManager,
            UserAuthenticationService userAuthenticationService,
            StudentRepository studentRepository) {
        this.authenticationManager = authenticationManager;
        this.userAuthenticationService = userAuthenticationService;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        try {
            var response = authenticate(httpRequest, request.emailAddress(), request.password());
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
        httpRequest.getSession().invalidate();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public LoginSuccessResponse register(@RequestBody RegisterRequest request, HttpServletRequest httpRequest) {
        userAuthenticationService.register(
                request.firstName(),
                request.lastName(),
                request.emailAddress(),
                request.password()
        );

        return authenticate(httpRequest, request.emailAddress(), request.password());
    }

    private LoginSuccessResponse authenticate(HttpServletRequest httpRequest, String email, String password) {
        // Authenticate the user with the provided credentials (email and password)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        // Save the authenticated user in the Spring security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Ensure a session is created for the authenticated user
        httpRequest.getSession(true);

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
