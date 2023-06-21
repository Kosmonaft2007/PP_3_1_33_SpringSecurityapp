package com.example.SpringSecurityapp.repository;

import com.example.SpringSecurityapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolsRepository extends JpaRepository<Role, Long> {
}
