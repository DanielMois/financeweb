package com.mois.financeweb.controllers;

import com.mois.financeweb.models.User;
import com.mois.financeweb.models.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register-form")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register-form";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user) {
        userService.register(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login-form";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user) {
        User authenticatedUser = userService.login(user.getName(), user.getPassword());
        if (authenticatedUser != null) {

            return "redirect:/main";
        }
        return "redirect:/login?error";
    }
}