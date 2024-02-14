package com.mois.financeweb.controllers;

import com.mois.financeweb.models.User;
import com.mois.financeweb.models.UserService;
import com.mois.financeweb.repositories.CreditPurchaseRepository;
import com.mois.financeweb.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/register-form")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register-form";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user) {

        if (userRepository.findByLogin(user.getLogin()) != null) {
            return "redirect:/register-form";
        }

        user.setCreateDate(new Date());
        userService.register(user);
        return "redirect:/login-form";
    }

    @GetMapping("/login-form")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login-form";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, HttpSession session) {
        User authenticatedUser = userService.login(user.getLogin(), user.getPassword());

        if (authenticatedUser != null) {
            session.setAttribute("userId", authenticatedUser.getId());
            return "redirect:/home";
        }
        return "redirect:/login-form";
    }
}