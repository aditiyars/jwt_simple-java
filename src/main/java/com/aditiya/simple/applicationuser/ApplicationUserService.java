package com.aditiya.simple.applicationuser;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aditiya.simple.authentication.model.UserPrincipal;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService{
    private final ApplicationUserRepository applicationUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username)
        );
        return UserPrincipal.build(applicationUser);
    }
}
