package net.hackyourfuture.coursehub.service;

import net.hackyourfuture.coursehub.data.UserAccountEntity;
import net.hackyourfuture.coursehub.repository.UserAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class UserAuthenticationService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;

    public UserAuthenticationService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        UserAccountEntity user = userAccountRepository.findByEmailAddress(emailAddress);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for provided email address");
        }
        return new org.springframework.security.core.userdetails.User(
                user.emailAddress(),
                user.passwordHash(),
                Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + user.role().name())));
    }
}
