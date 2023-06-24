package com.example.SpringSecurityapp.security;

import com.example.SpringSecurityapp.models.User;
import com.example.SpringSecurityapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthencationProvider implements AuthenticationProvider {
    @Autowired
    private UserRepository uR;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        User myUser = uR.findByUsername(name);
        if (myUser == null) {
            throw new BadCredentialsException("Unknown user " + name);
        }
        if (!password.equals(myUser.getPassword())) {
            throw new BadCredentialsException("Bad password");
        }
        UserDetails principal = User.builder()
                .username(myUser.getName())
                .password(myUser.getPassword())
                .roles(myUser.getLastName())
                .build();
        return new UsernamePasswordAuthenticationToken(principal, password, principal.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}