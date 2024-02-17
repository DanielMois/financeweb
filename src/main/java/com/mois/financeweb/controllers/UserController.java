package com.mois.financeweb.controllers;

import com.mois.financeweb.dto.CreditPurchaseRequisition;
import com.mois.financeweb.dto.UserRequisition;
import com.mois.financeweb.models.User;
import com.mois.financeweb.models.UserService;
import com.mois.financeweb.repositories.CreditPurchaseRepository;
import com.mois.financeweb.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    //Mapeando caminho para formulário de registro inicial
    @GetMapping("/register-form")
    public String showRegisterForm(Model model, UserRequisition req) {
        model.addAttribute("user", new User());
        return "register-form";
    }

    //Mapeando criação de novo usuário
//    @PostMapping("/register")
//    public String register(@ModelAttribute("user") User user) {
//
//        if (userRepository.findByLogin(user.getLogin()) != null) {
//            return "redirect:/register-form";
//        }
//
//        userService.register(user);
//        return "redirect:/login-form";
//    }

        @ModelAttribute(value = "newUserRequisition")
        public UserRequisition newUserRequisition()
        {
            return new UserRequisition();
        }

    @PostMapping("/register")
    public ModelAndView register(@Valid UserRequisition req, BindingResult result) {

        ModelAndView mv = new ModelAndView("register-form");

        if(result.hasErrors()) {
            mv.addObject("userRequisition", req);
            return mv;
        }

        User user = req.toUser();
        userService.register(user);
        mv.addObject("error", false);
        mv.setViewName("redirect:/home");

        return mv;
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