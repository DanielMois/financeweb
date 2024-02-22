package com.mois.financeweb.controllers;

import com.mois.financeweb.dto.CreditPurchaseRequisition;
import com.mois.financeweb.models.Category;
import com.mois.financeweb.models.CreditPurchase;
import com.mois.financeweb.models.User;
import com.mois.financeweb.models.UserService;
import com.mois.financeweb.repositories.CreditPurchaseRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.text.SimpleDateFormat;


@Controller
public class CreditPurchaseController {
    @Autowired
    private final CreditPurchaseRepository creditPurchaseRepository;
    @Autowired
    private final UserService userService;

    @Autowired
    public CreditPurchaseController(CreditPurchaseRepository creditPurchaseRepository, UserService userService) {
        this.creditPurchaseRepository = creditPurchaseRepository;
        this.userService = userService;
    }

    @GetMapping("/credit-purchases")
    public ModelAndView index(@RequestParam(name = "invoice", required = false) String invoice, HttpSession session) {

        Long userId = userService.getCurrentUserId(session);

        if (userId == null) {
            return new ModelAndView("redirect:/");
        }

        List<CreditPurchase> creditPurchases = creditPurchaseRepository.findByUserId(userId);

        ModelAndView mv = new ModelAndView("credit-purchases/index");
        mv.addObject("creditPurchases", creditPurchases);

        return mv;
    }

    @GetMapping("/credit-purchases/new")
    public ModelAndView newCreditPurchase(CreditPurchaseRequisition req) {
        ModelAndView mv = new ModelAndView("credit-purchases/new");
        mv.addObject("categories", Category.values());

        return mv;
    }

        @ModelAttribute(value = "newCreditPurchaseRequisition")
        public CreditPurchaseRequisition newCreditPurchaseRequisition()
        {
            return new CreditPurchaseRequisition();
        }

    @PostMapping("/credit-purchases")
    public ModelAndView createCreditPurchase(@Valid CreditPurchaseRequisition req, BindingResult result, HttpSession session) {

        Long userId = userService.getCurrentUserId(session);

        User currentUser = userService.findById(userId);

        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("credit-purchases/new");
            mv.addObject("categories", Category.values());

            return mv;
        }
        else {

            CreditPurchase creditPurchase = req.toCreditPurchase();
            creditPurchase.setUser(currentUser);
            creditPurchase.setCreateDate(new Date());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");


            System.out.println(creditPurchase.getCreateDate());
            creditPurchase.setInvoice(dateFormat.format(creditPurchase.getCreateDate()));
            this.creditPurchaseRepository.save(creditPurchase);

            ModelAndView mv = new ModelAndView("redirect:/credit-purchases/" + creditPurchase.getId());
            mv.addObject("error", false);
            mv.addObject("message", "Credit Purchase #" + creditPurchase.getId() + " created successfully!");

            return mv;
        }
    }

    @GetMapping("/credit-purchases/{id}")
    public ModelAndView show(@PathVariable Long id) {
        Optional<CreditPurchase> optional = this.creditPurchaseRepository.findById(id);

        if(optional.isPresent()) {
            CreditPurchase creditPurchase = optional.get();
            ModelAndView mv = new ModelAndView("credit-purchases/show");
            mv.addObject("creditPurchase", creditPurchase);
            return mv;
        } else {
            return new ModelAndView("redirect:/credit-purchases");
        }
    }

    @GetMapping("/credit-purchases/{id}/edit")
    public ModelAndView edit(@PathVariable long id, CreditPurchaseRequisition req){

        Optional<CreditPurchase> optional = this.creditPurchaseRepository.findById(id);

        if (optional.isPresent()) {
            CreditPurchase creditPurchase = optional.get();
            req.fromCreditPurchase(creditPurchase);

            ModelAndView mv = new ModelAndView("credit-purchases/edit");
            mv.addObject("creditPurchaseId", creditPurchase.getId());
            mv.addObject("categories", Category.values());
            return mv;
        } else {
            return new ModelAndView("redirect:/credit-purchases");
        }
    }

    @PostMapping("/credit-purchases/{id}")
    public ModelAndView update(@PathVariable long id, @Valid CreditPurchaseRequisition req, BindingResult result) {

        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("credit-purchases/edit");
            mv.addObject("creditPurchaseId", id);
            mv.addObject("categories", Category.values());
            return mv;
        }

        else {
            Optional<CreditPurchase> optional = this.creditPurchaseRepository.findById(id);

            if(optional.isPresent()) {

                CreditPurchase creditPurchase = req.toCreditPurchase(optional.get());
                creditPurchase.setInvoice(optional.get().getInvoice());
                this.creditPurchaseRepository.save(creditPurchase);

                ModelAndView mv = new ModelAndView("redirect:/credit-purchases/" + creditPurchase.getId());
                mv.addObject("error", false);
                mv.addObject("message", "Credit Purchase #" + id + " updated successfully!");

                return mv;
            } else {
                ModelAndView mv = new ModelAndView("redirect:/credit-purchases");
                mv.addObject("error", true);
                mv.addObject("message", "Credit Purchase #" + id + " not found.");

                return mv;
            }
        }
    }

    @GetMapping("/credit-purchases/{id}/delete")
    public ModelAndView delete(@PathVariable long id) {
        ModelAndView mv = new ModelAndView("redirect:/credit-purchases");
        Optional<CreditPurchase> optional = this.creditPurchaseRepository.findById(id);

        if (optional.isPresent())
            try{
                System.out.println(optional);
                this.creditPurchaseRepository.deleteById(id);
                mv.addObject("error", false);
                mv.addObject("message", "Credit Purchase #" + id + " deleted successfully!");

            } catch (Exception e) {
                mv.addObject("error", true);
                mv.addObject("message", "An error occurred while deleting Credit Purchase #" + id + ": " + e);

            }

        else {
            mv.addObject("error", true);
            mv.addObject("message", "Credit Purchase #" + id + " not found.");
        }


        return mv;
    }

}
