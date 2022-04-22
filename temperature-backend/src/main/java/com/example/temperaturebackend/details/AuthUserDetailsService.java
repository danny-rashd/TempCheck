package com.example.temperaturebackend.details;

import com.example.temperaturebackend.entity.AuthUser;
import com.example.temperaturebackend.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserRepository.findByUsername(username);
        if (authUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new AuthUserDetails(authUser);
    }

}
