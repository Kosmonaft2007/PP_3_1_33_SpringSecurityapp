package com.example.SpringSecurityapp.servis;

import com.example.SpringSecurityapp.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    void add (User user);

    void update (User user);

    void delete (Long id);

    List<User> getAll ();

    User show (Long id);

    User findByUsername (String name);
}
