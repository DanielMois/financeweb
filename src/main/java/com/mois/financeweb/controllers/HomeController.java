package com.mois.financeweb.controllers;

import com.mois.financeweb.models.User;
import com.mois.financeweb.models.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes("userId")
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {

        Long userId = userService.getCurrentUserId(session);

        User currentUser = userService.findById(userId);
        model.addAttribute("currentBalance", currentUser.getCurrentBalance());
        model.addAttribute("username", currentUser.getName());


        return "home";
    }

    @PostMapping("/logout")
    public String logout(Model model, HttpSession session) {
        userService.logout();
        return "redirect:/";
    }
}
