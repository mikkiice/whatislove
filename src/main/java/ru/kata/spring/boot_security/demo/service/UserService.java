package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;


public interface  UserService extends UserDetailsService {

    List<User> getUsers();
    User getUsers(Long id);
    User findByUsername(String username);
    void createUser(String username, String password, Set<Role> roles);
    void update(Long id,String username, String password);
    void delete(Long id);

}
