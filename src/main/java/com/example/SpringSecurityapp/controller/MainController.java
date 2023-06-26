package com.example.SpringSecurityapp.controller;


import com.example.SpringSecurityapp.models.Role;
import com.example.SpringSecurityapp.models.User;
import com.example.SpringSecurityapp.servis.Impl.RoleServiceImpl;
import com.example.SpringSecurityapp.servis.Impl.UserServiceImpl;
import com.example.SpringSecurityapp.servis.RoleService;
import com.example.SpringSecurityapp.servis.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class MainController {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public MainController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String printUsers(ModelMap model) {
        List<User> listOfUsers = userService.getAll();
        model.addAttribute("listOfUsers", listOfUsers);
        return "Users";
    }

    @GetMapping("/addNewUser")
    public String addNewUser(ModelMap model) {
        User user = new User();
        Collection<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "new-user-info";
    }

    @PostMapping("/")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin/";
    }

    @GetMapping("/{id}/edit")
    public String editUser(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.show(id));
        return "/editUser";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.update(user);
        return "redirect:/admin/";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/";
    }
}

