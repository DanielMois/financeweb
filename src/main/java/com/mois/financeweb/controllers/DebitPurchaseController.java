package com.mois.financeweb.controllers;

import com.mois.financeweb.dto.DebitPurchaseRequisition;
import com.mois.financeweb.models.Category;
import com.mois.financeweb.models.DebitPurchase;
import com.mois.financeweb.models.TransactionType;
import com.mois.financeweb.repositories.DebitPurchaseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/debit-purchases")
public class DebitPurchaseController {
    @Autowired
    private DebitPurchaseRepository debitPurchaseRepository;

    @GetMapping("")
    public ModelAndView index() {

        List<DebitPurchase> debitPurchases = this.debitPurchaseRepository.findAll();

        ModelAndView mv = new ModelAndView("debit-purchases/index");
        mv.addObject("debitPurchases", debitPurchases);

        return mv;
    }

    @GetMapping("/new")
    public ModelAndView newDebitPurchase(DebitPurchaseRequisition req) {
        ModelAndView mv = new ModelAndView("debit-purchases/new");
        mv.addObject("categories", Category.values());
        mv.addObject("transactionTypes", TransactionType.values());

        return mv;
    }

    @ModelAttribute(value = "newDebitPurchaseRequisition")
    public DebitPurchaseRequisition newDebitPurchaseRequisition()
    {
        return new DebitPurchaseRequisition();
    }

    @PostMapping("")
    public ModelAndView createDebitPurchase(@Valid DebitPurchaseRequisition req, BindingResult result) {
        if (result.hasErrors()) {

            ModelAndView mv = new ModelAndView("debit-purchases/new");
            mv.addObject("categories", Category.values());
            mv.addObject("transactionTypes", TransactionType.values());

            mv.addObject("error", false);
            mv.addObject("message", "Something went wrong.");
            return mv;
        }
        else {
            DebitPurchase debitPurchase = req.toDebitPurchase();
            this.debitPurchaseRepository.save(debitPurchase);

            ModelAndView mv = new ModelAndView("redirect:/debit-purchases/" + debitPurchase.getId());
            mv.addObject("error", false);
            mv.addObject("message", "Debit Purchase #" + debitPurchase.getId() + " created successfully!");
            return mv;
        }
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id) {
        Optional<DebitPurchase> optional = this.debitPurchaseRepository.findById(id);

        if(optional.isPresent()) {
            DebitPurchase debitPurchase = optional.get();
            ModelAndView mv = new ModelAndView("debit-purchases/show");
            mv.addObject("debitPurchase", debitPurchase);
            return mv;
        } else {
            return new ModelAndView("redirect:/debit-purchases");
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable long id, DebitPurchaseRequisition req){

        Optional<DebitPurchase> optional = this.debitPurchaseRepository.findById(id);

        if (optional.isPresent()) {
            DebitPurchase debitPurchase = optional.get();
            req.fromDebitPurchase(debitPurchase);

            ModelAndView mv = new ModelAndView("debit-purchases/edit");
            mv.addObject("debitPurchaseId", debitPurchase.getId());
            mv.addObject("categories", Category.values());
            mv.addObject("transactionTypes", TransactionType.values());
            return mv;
        } else {
            return new ModelAndView("redirect:/debit-purchases");
        }
    }

    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable long id, @Valid DebitPurchaseRequisition req, BindingResult result) {

        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("debit-purchases/edit");
            mv.addObject("debitPurchaseId", id);
            mv.addObject("categories", Category.values());
            mv.addObject("transactionTypes", TransactionType.values());
            return mv;
        }

        else {
            Optional<DebitPurchase> optional = this.debitPurchaseRepository.findById(id);

            if(optional.isPresent()) {
                DebitPurchase debitPurchase = req.toDebitPurchase(optional.get());
                this.debitPurchaseRepository.save(debitPurchase);

                ModelAndView mv = new ModelAndView("redirect:/debit-purchases/" + debitPurchase.getId());
                mv.addObject("error", false);
                mv.addObject("message", "Debit Purchase #" + debitPurchase.getId() + " updated successfully!");

                return mv;
            } else {
                ModelAndView mv = new ModelAndView("redirect:/debit-purchases");
                mv.addObject("error", true);
                mv.addObject("message", "Something went wrong.");

                return mv;
            }
        }
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable long id) {
        ModelAndView mv = new ModelAndView("redirect:/debit-purchases");
        Optional<DebitPurchase> optional = this.debitPurchaseRepository.findById(id);

        if (optional.isPresent())
            try{
                this.debitPurchaseRepository.deleteById(id);
                mv.addObject("error", false);
                mv.addObject("message", "Debit Purchase #" + id + " deleted successfully!");

            } catch (Exception e) {
                mv.addObject("error", true);
                mv.addObject("message", "An error occurred while deleting Debit Purchase #" + id + ": " + e);

            }

        else {
            mv.addObject("error", true);
            mv.addObject("message", "Debit Purchase #" + id + " not found.");
        }

        return mv;
    }

}
