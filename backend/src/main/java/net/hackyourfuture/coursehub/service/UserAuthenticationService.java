package net.hackyourfuture.coursehub.service;

import net.hackyourfuture.coursehub.data.AuthenticatedUser;
import net.hackyourfuture.coursehub.data.InstructorEntity;
import net.hackyourfuture.coursehub.data.StudentEntity;
import net.hackyourfuture.coursehub.data.UserAccountEntity;
import net.hackyourfuture.coursehub.repository.InstructorRepository;
import net.hackyourfuture.coursehub.repository.StudentRepository;
import net.hackyourfuture.coursehub.repository.UserAccountRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserAuthenticationService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAuthenticationService(
            UserAccountRepository userAccountRepository,
            StudentRepository studentRepository,
            InstructorRepository instructorRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userAccountRepository = userAccountRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthenticatedUser loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        UserAccountEntity user = userAccountRepository.findByEmailAddress(emailAddress);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for provided emailAddress address");
        }

        return switch (user.role()) {
            case student -> {
                StudentEntity student = studentRepository.findById(user.userId());
                yield new AuthenticatedUser(user.userId(), student.firstName(), student.lastName(), user.emailAddress(), user.passwordHash(), user.role());
            }
            case instructor -> {
                InstructorEntity instructor = instructorRepository.findById(user.userId());
                yield new AuthenticatedUser(user.userId(), instructor.firstName(), instructor.lastName(), user.emailAddress(), user.passwordHash(), user.role());
            }
        };
    }

    public AuthenticatedUser currentAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            var principal = authentication.getPrincipal();
            if (principal instanceof AuthenticatedUser user) {
                return user;
            }
        }
        throw new IllegalStateException("No AuthenticatedUser");
    }

    public void register(String firstName, String lastName, String emailAddress, String password) {
        // For simplicity, we will register every new user as a student
        // In a real-world application, you might want to allow registering as an instructor as well
        // and have an admin approve instructor accounts before they can log in
        var passwordHash = passwordEncoder.encode(password);
        studentRepository.insertStudent(firstName, lastName, emailAddress, passwordHash);
    }

    /**
     * Generates a new API key for the current authenticated user.
     * @return the generated API key
     * @throws IllegalStateException if no user is authenticated
     */
    public String generateApiKey() {
        AuthenticatedUser authenticatedUser = currentAuthenticatedUser();
        if (authenticatedUser == null) {
            throw new IllegalStateException("No authenticated user found");
        }

        // Generate a secure random API key with a prefix to identify it as an API key
        byte[] randomBytes = new byte[32];
        try {
            SecureRandom.getInstanceStrong().nextBytes(randomBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Unable to generate an authentication token", e);
        }
        String apiKey = "chub_" + Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

        // Store the API key in the database
        userAccountRepository.updateApiKey(authenticatedUser.getUserId(), apiKey);

        return apiKey;
    }

    /**
     * Finds a user by their API key.
     * @param apiKey the API key
     * @return the authenticated user or null if not found
     */
    public AuthenticatedUser findUserByApiKey(String apiKey) {
        UserAccountEntity userAccount = userAccountRepository.findByApiKey(apiKey);
        if (userAccount == null) {
            return null;
        }

        return buildAuthenticatedUser(userAccount);
    }

    private AuthenticatedUser buildAuthenticatedUser(UserAccountEntity userAccount) {
        return switch (userAccount.role()) {
            case student -> {
                StudentEntity student = studentRepository.findById(userAccount.userId());
                yield new AuthenticatedUser(userAccount.userId(), student.firstName(), student.lastName(), userAccount.emailAddress(), userAccount.passwordHash(), userAccount.role());
            }
            case instructor -> {
                InstructorEntity instructor = instructorRepository.findById(userAccount.userId());
                yield new AuthenticatedUser(userAccount.userId(), instructor.firstName(), instructor.lastName(), userAccount.emailAddress(), userAccount.passwordHash(), userAccount.role());
            }
        };
    }
}
