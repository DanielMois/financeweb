package com.mois.financeweb.controllers;

import com.mois.financeweb.models.ChartService;
import com.mois.financeweb.models.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ChartController {

    @Autowired
    private ChartService chartService;

    @Autowired
    private final UserService userService;

    @Autowired
    public ChartController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/credit-purchases/getInvoiceValues")
    public List<Object[]> getTotalPriceByInvoice(HttpSession session) {
        Long userId = userService.getCurrentUserId(session);

        return chartService.getTotalPriceByInvoice(userId);
    }

    @GetMapping("/credit-purchases/data-analysis/invoice-values")
    public ModelAndView showTotalPriceByInvoice() {
        return new ModelAndView("/credit-purchases/data-analysis/invoice-values");
    }

    @GetMapping("/credit-purchases/getCategoryValues")
    public List<Object[]> getTotalPriceByCategory(HttpSession session) {
        Long userId = userService.getCurrentUserId(session);
        return chartService.cpGetTotalPriceByCategory(userId);
    }

    @GetMapping("/credit-purchases/data-analysis/category-values")
    public ModelAndView cpShowTotalPriceByCategory() {
        return new ModelAndView(("/credit-purchases/data-analysis/category-values"));
    }

    @GetMapping("/debit-purchases/getTransactionTypeValues")
    public List<Object[]> getTotalPriceByTransactionType(HttpSession session) {
        Long userId = userService.getCurrentUserId(session);

        return chartService.getTotalPriceByTransactionType(userId);
    }

    @GetMapping("/debit-purchases/data-analysis/transaction-type-values")
    public ModelAndView showTotalPriceByTransactionType() {
        return new ModelAndView("/debit-purchases/data-analysis/transaction-type-values");
    }

    @GetMapping("/debit-purchases/getCategoryValues")
    public List<Object[]> dpGetTotalPriceByCategory(HttpSession session) {
        Long userId = userService.getCurrentUserId(session);

        return chartService.dpGetTotalPriceByCategory(userId);
    }

    @GetMapping("/debit-purchases/data-analysis/category-values")
    public ModelAndView dpShowTotalPriceByCategory() {
        return new ModelAndView(("/debit-purchases/data-analysis/category-values"));
    }

}

