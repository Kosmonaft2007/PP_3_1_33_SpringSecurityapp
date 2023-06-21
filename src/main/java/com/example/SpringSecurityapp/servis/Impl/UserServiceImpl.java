package com.example.SpringSecurityapp.servis.Impl;

import com.example.SpringSecurityapp.models.Role;
import com.example.SpringSecurityapp.models.User;
import com.example.SpringSecurityapp.repository.UserRepository;
import com.example.SpringSecurityapp.servis.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// задача по имини пользователя предоставить User
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private UserRepository uR;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository uR, @Lazy PasswordEncoder passwordEncoder) {
        this.uR = uR;
        this.passwordEncoder = passwordEncoder;
    }

    //делавем обертку над UserRepository что бы напрямую к нему не обращаться
    public User findByUsername(String name) {
        return uR.findByUsername(name);
    }

    //нам дают имя пользователя UserRepository, и по нем вернуть самого юзера из БД
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // дастоем из бады пользователя по пришедшему имени
        User user = uR.findByUsername(username); //если есть то получим если нет то null
        // если не нашли то мы просим new UsernameNotFoundException (что то нет таких людей)
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        //если нашли
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    // нужно из коллекции ролей получаем коллекцию прав доступа

    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthority (Collection<Role> roles) {
        return roles.stream().map(r ->new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    @Transactional
    public void add (User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        uR.save(user);
    }

    @Transactional
    public void update (User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        uR.save(user);
    }

    @Transactional
    public void delete (Long id) {
        uR.delete(uR.findById(id).get());
    }

    @Transactional
    public List<User> getAll () {
        return uR.findAll();
    }

    @Transactional
    public User show (Long id) {
        return uR.findById(id).get();
    }


}
