package com.example.SpringSecurityapp.models;

import lombok.Data;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.persistence.*;
import javax.sql.DataSource;
import java.util.Collection;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Long age;


    @ManyToMany( fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "role_id", nullable = false, updatable = false)
    })
    private Collection<Role> roles;

    public User() {
    }

    //master

    //jhfgfhjkmnhvvbn

//    public User(String name, String lastName, Long age) {
//        this.name = name;
//        this.lastName = lastName;
//        this.age = age;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public Long getAge() {
//        return age;
//    }
//
//    public Collection<Role> getRoles() {
//        return roles;
//    }
//
//    public Long getId() {
//        return id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public void setAge(Long age) {
//        this.age = age;
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "name='" + name + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", age=" + age +
//                '}';
//    }
}
