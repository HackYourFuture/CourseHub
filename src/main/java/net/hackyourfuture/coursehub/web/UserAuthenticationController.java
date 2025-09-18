package net.hackyourfuture.coursehub.web;

import jakarta.servlet.http.HttpServletRequest;
import net.hackyourfuture.coursehub.data.InstructorEntity;
import net.hackyourfuture.coursehub.data.StudentEntity;
import net.hackyourfuture.coursehub.data.UserAccountEntity;
import net.hackyourfuture.coursehub.repository.InstructorRepository;
import net.hackyourfuture.coursehub.repository.StudentRepository;
import net.hackyourfuture.coursehub.repository.UserAccountRepository;
import net.hackyourfuture.coursehub.web.model.HttpErrorResponse;
import net.hackyourfuture.coursehub.web.model.LoginRequest;
import net.hackyourfuture.coursehub.web.model.LoginSuccessResponse;
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
    private final UserAccountRepository userAccountRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;

    public UserAuthenticationController(
            AuthenticationManager authenticationManager,
            UserAccountRepository userAccountRepository,
            StudentRepository studentRepository,
            InstructorRepository instructorRepository) {
        this.authenticationManager = authenticationManager;
        this.userAccountRepository = userAccountRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        try {
            // Authenticate the user with the provided credentials (email and password)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.emailAddress(), request.password()));
            // Save the authenticated user in the Spring security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Ensure a session is created for the authenticated user
            httpRequest.getSession(true);

            // Retrieve the corresponding user data from the database to return
            UserAccountEntity user = userAccountRepository.findByEmailAddress(request.emailAddress());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new HttpErrorResponse("No user found for provided email address"));
            }

            String firstName = null;
            String lastName = null;
            switch (user.role()) {
                case student -> {
                    StudentEntity student = studentRepository.findById(user.userId());
                    firstName = student.firstName();
                    lastName = student.lastName();
                }
                case instructor -> {
                    InstructorEntity instructor = instructorRepository.findById(user.userId());
                    firstName = instructor.firstName();
                    lastName = instructor.lastName();
                }
            }
            return ResponseEntity.ok(
                    new LoginSuccessResponse(user.userId(), firstName, lastName, user.emailAddress(), user.role()));
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
}