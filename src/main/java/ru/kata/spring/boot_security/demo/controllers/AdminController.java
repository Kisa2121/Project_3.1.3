package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String index(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "admin/index";
    }


    @GetMapping("/show")
    public String show(@RequestParam Long id, Model model) {
        model.addAttribute("user", userService.get(id));
        return "admin/show";
    }


    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/new";
    }


    @PostMapping
    public String create(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin";
    }


    @GetMapping("/edit")
    public String edit(@RequestParam Long id, Model model) {
        model.addAttribute("user", userService.get(id));
        return "admin/edit";
    }


    @PostMapping("/update")
    public String update(@RequestParam Long id, @RequestParam String username, @RequestParam String lastName) {
        User user = userService.get(id);
        user.setName(username);
        user.setLastName(lastName);

        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
