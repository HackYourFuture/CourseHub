package net.hackyourfuture.coursehub.web;

import net.hackyourfuture.coursehub.data.UserAccountEntity;
import net.hackyourfuture.coursehub.repository.UserAccountRepository;
import net.hackyourfuture.coursehub.web.model.LoginRequest;
import net.hackyourfuture.coursehub.web.model.LoginResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserAccountRepository userAccountRepository;

    public UserAuthenticationController(
            AuthenticationManager authenticationManager,
            UserAccountRepository userAccountRepository) {
        this.authenticationManager = authenticationManager;
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.emailAddress(), request.password()));
            UserAccountEntity user = userAccountRepository.findByEmailAddress(request.emailAddress());
            return new LoginResponse(user.userId(), authentication.isAuthenticated(), null);
        } catch (AuthenticationException e) {
            if (e instanceof BadCredentialsException) {
                return new LoginResponse(null, false, "Invalid credentials provided");
            }
            if (e instanceof AuthenticationCredentialsNotFoundException) {
                return new LoginResponse(null, false, "No user found for provided email address");
            }
            return new LoginResponse(null, false, "Something went wrong");
        }
    }
}
