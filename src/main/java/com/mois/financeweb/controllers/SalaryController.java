package com.mois.financeweb.controllers;

import com.mois.financeweb.models.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class SalaryController {
    @Autowired
    private UserService userService;

    @PostMapping("/salary")
    public void updateSalary(@RequestParam Long userId, @RequestParam BigDecimal salary) {
        userService.updateSalary(userId, salary);
    }
}
