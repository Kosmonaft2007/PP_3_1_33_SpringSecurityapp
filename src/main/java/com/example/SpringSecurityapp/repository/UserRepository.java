package com.example.SpringSecurityapp.repository;


import com.example.SpringSecurityapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //findByUsername - поиск User по его username
    @Query("Select u from User u left join fetch u.roles where u.name=:name")
    User findByUsername(String name);

    @Override
    Optional<User> findById(Long id);

    User findByName(String name);
}
