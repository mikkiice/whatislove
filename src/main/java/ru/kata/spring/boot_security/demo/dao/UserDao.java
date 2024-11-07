package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    public void create(User user);
    public void delete(Long id);
    public void update(User user);
    public User read(Long id);
    public User findByUsername(String username);
    public List<User> getAllUsers();
    public User findUserWithRoleByUsername(String username);

}
