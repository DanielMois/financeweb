package com.mois.financeweb.controllers;

import com.mois.financeweb.dto.CreditPurchaseRequisition;
import com.mois.financeweb.models.Category;
import com.mois.financeweb.models.CreditPurchase;
import com.mois.financeweb.repositories.CreditPurchaseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/credit-purchases")
public class CreditPurchaseController {
    @Autowired
    private CreditPurchaseRepository creditPurchaseRepository;

    @GetMapping("")
    public ModelAndView index(@RequestParam(name = "invoice", required = false) String invoice) {

        List<CreditPurchase> creditPurchases;

        creditPurchases = this.creditPurchaseRepository.findAll();

        ModelAndView mv = new ModelAndView("credit-purchases/index");
        mv.addObject("creditPurchases", creditPurchases);

        return mv;
    }

    @GetMapping("/new")
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

    @PostMapping("")
    public ModelAndView createCreditPurchase(@Valid CreditPurchaseRequisition req, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("credit-purchases/new");
            mv.addObject("categories", Category.values());

            return mv;
        }
        else {
            CreditPurchase creditPurchase = req.toCreditPurchase();
            this.creditPurchaseRepository.save(creditPurchase);
            ModelAndView mv = new ModelAndView("redirect:/credit-purchases/" + creditPurchase.getId());
            mv.addObject("error", false);
            mv.addObject("message", "Credit Purchase #" + creditPurchase.getId() + " created successfully!");

            return mv;
        }
    }

    @GetMapping("/{id}")
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

    @GetMapping("/{id}/edit")
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

    @PostMapping("/{id}")
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

    @GetMapping("/{id}/delete")
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
