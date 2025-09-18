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
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;

    public UserAuthenticationService(
            UserAccountRepository userAccountRepository,
            StudentRepository studentRepository,
            InstructorRepository instructorRepository
    ) {
        this.userAccountRepository = userAccountRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
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
                yield new AuthenticatedUser(user.userId(), student.firstName(), student.lastName(), user.emailAddress(), user.role());
            }
            case instructor -> {
                InstructorEntity instructor = instructorRepository.findById(user.userId());
                yield new AuthenticatedUser(user.userId(), instructor.firstName(), instructor.lastName(), user.emailAddress(), user.role());
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
        studentRepository.insertStudent(firstName, lastName, emailAddress, password);
    }
}
