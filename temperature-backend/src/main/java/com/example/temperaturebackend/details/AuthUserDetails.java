package com.example.temperaturebackend.details;

import com.example.temperaturebackend.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class AuthUserDetails implements UserDetails {


    private UserEntity userEntity;

    public AuthUserDetails(UserEntity userEntity) {
        super();
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole()));
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userEntity.isEnabled();
    }

    public String getFirstName() {
        return userEntity.getFirstName();
    }

    public String getLastName() {
        return userEntity.getLastName();
    }

    public String getFullName() {
        return userEntity.getFirstName() + " " + userEntity.getLastName();
    }


}
