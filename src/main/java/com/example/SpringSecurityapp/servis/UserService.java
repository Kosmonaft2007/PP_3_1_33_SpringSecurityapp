package com.example.SpringSecurityapp.servis;

import com.example.SpringSecurityapp.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;

public interface UserService  {

    void add(User user);

    void update(User user);

    void delete(Long id);

    List<User> getAll();

    User show(Long id);

    User findByUsername(String name);

}
