package net.hackyourfuture.coursehub.data;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public final class AuthenticatedUser implements UserDetails,
                                                CredentialsContainer {
    private final Integer userId;
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final Role role;
    private String passwordHash;

    public AuthenticatedUser(
            Integer userId,
            String firstName,
            String lastName,
            String emailAddress,
            String passwordHash,
            Role role
    ) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    @Override
    public String getUsername() {
        return emailAddress;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public void eraseCredentials() {
        passwordHash = null;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Role getRole() {
        return role;
    }
}
