package com.example.SpringSecurityapp.servis;

import com.example.SpringSecurityapp.models.User;
import com.example.SpringSecurityapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository uR;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User myUser= uR.findByUsername(userName);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: "+userName);
        }
//        UserDetails user = User.builder()
//                .username(myUser.getName())
//                .password(myUser.getPassword())
//                .roles(myUser.getLastName())
//                .build();
        return (UserDetails) myUser;
    }
}