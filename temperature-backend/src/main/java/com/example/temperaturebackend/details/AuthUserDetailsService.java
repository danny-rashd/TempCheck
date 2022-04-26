package com.example.temperaturebackend.details;

import com.example.temperaturebackend.entity.UserEntity;
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
        UserEntity userEntity = authUserRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new AuthUserDetails(userEntity);
    }

}
