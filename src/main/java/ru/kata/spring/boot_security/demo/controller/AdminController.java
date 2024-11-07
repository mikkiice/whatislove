package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;



@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping
    public String userList(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getUsers());
        return "admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user.getUsername(), user.getPassword(), user.getRoles());
        return "redirect:/admin";
    }
    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new_user";
    }
    @PostMapping("new")
    public String createUser(@ModelAttribute User user) {
        userService.createUser(user.getUsername(), user.getPassword(), user.getRoles());
        return "redirect:/admin";
    }
    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user.getId(), user.getUsername(), user.getPassword());
        return "redirect:/admin";
    }
    @GetMapping("/users/edit")
    public String editUser(@RequestParam Long id, Model model) {
        model.addAttribute("user", userService.getUsers(id));
        return "edit";
    }
    @PostMapping("find")
    public String findUser(@RequestParam Long id, Model model) {
        User user = userService.getUsers(id);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("user", user);
        return "admin";
    }
}
