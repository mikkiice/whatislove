package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    @Transactional
    public void createUser(String username, String password, Set<Role> roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);
        Set<Role> rolesSet = new HashSet<>();
        if (roles.equals("admin")) {
            rolesSet.add(roleDao.findByName("ROLE_ADMIN"));
        }
        user.setRoles(rolesSet);
        userDao.create(user);
    }

    @Override
    public List<User> getUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public void update(Long id, String username, String password) {
        User user = userDao.findUserWithRoleByUsername(username);
        if (user != null) {
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            userDao.update(user);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    public User getUsers(Long id) {
        return userDao.read(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserWithRoleByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format(username,"User not found"));
        }
        return user;
    }

    public List<Role> getRoles() {
        return roleDao.getAllRoles();
    }
}
