package com.example.SpringSecurityapp.servis.Impl;

import com.example.SpringSecurityapp.models.Role;
import com.example.SpringSecurityapp.repository.RolsRepository;
import com.example.SpringSecurityapp.servis.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private RolsRepository rolsRepository;

    @Autowired
    public RoleServiceImpl(RolsRepository rolsRepository) {
        this.rolsRepository = rolsRepository;
    }

    public List<Role> getAllRoles() {
        return rolsRepository.findAll();
    }
}
