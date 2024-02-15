package com.mois.financeweb.controllers;

import com.mois.financeweb.models.User;
import com.mois.financeweb.models.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;


@Controller
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


        return "home";
    }
}
