package com.example.SpringSecurityapp.controller;

import com.example.SpringSecurityapp.models.User;
import com.example.SpringSecurityapp.servis.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class RegistrationController {
    private final UserService userServiceImpl;

    @Autowired
    public RegistrationController(UserService userServiceImp) {
        this.userServiceImpl = userServiceImp;
    }

    @GetMapping("/user")
    public String onlyForUser(Principal principal, ModelMap model) {
        User user = userServiceImpl.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }
}