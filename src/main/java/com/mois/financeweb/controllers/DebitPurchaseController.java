package com.mois.financeweb.controllers;

import com.mois.financeweb.dto.DebitPurchaseRequisition;
import com.mois.financeweb.models.*;
import com.mois.financeweb.repositories.DebitPurchaseRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/debit-purchases")
public class DebitPurchaseController {
    @Autowired
    private final DebitPurchaseRepository debitPurchaseRepository;
    @Autowired
    private final DebitPurchaseService debitPurchaseService;
    @Autowired
    private final UserService userService;

    @Autowired
    public DebitPurchaseController(DebitPurchaseRepository debitPurchaseRepository,DebitPurchaseService debitPurchaseService, UserService userService) {
        this.debitPurchaseRepository = debitPurchaseRepository;
        this.debitPurchaseService = debitPurchaseService;
        this.userService = userService;
    }

    @GetMapping("")
    public ModelAndView index(HttpSession session) {

        Long userId = userService.getCurrentUserId(session);

        List<DebitPurchase> debitPurchases = debitPurchaseRepository.findByUserId(userId);

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
            DebitPurchase debitPurchase = debitPurchaseService.createDebitPurchase(req);

            ModelAndView mv = new ModelAndView("redirect:/debit-purchases/" + debitPurchase.getId());
            mv.addObject("error", false);
            mv.addObject("message", "Debit Purchase #" + debitPurchase.getId() + " created successfully!");
            return mv;
        }
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id) {
        
        @SuppressWarnings("null")
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
                DebitPurchase updatedDebitPurchase = debitPurchaseService.editDebitPurchase(optional.get(), req);

                ModelAndView mv = new ModelAndView("redirect:/debit-purchases/" + updatedDebitPurchase.getId());
                mv.addObject("error", false);
                mv.addObject("message", "Debit Purchase #" + updatedDebitPurchase.getId() + " updated successfully!");

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
    public ModelAndView delete(@PathVariable long id, HttpSession session) {
        ModelAndView mv = new ModelAndView("redirect:/debit-purchases");
        Optional<DebitPurchase> optional = this.debitPurchaseRepository.findById(id);

        if (optional.isPresent())
            try{

                Long userId = userService.getCurrentUserId(session);
                User currentUser = userService.findById(userId);

                DebitPurchase debitPurchaseToDelete = optional.get();
                BigDecimal price = debitPurchaseToDelete.getPrice();
                TransactionType transactionType = debitPurchaseToDelete.getTransactionType();

                if (transactionType == TransactionType.IN) {
                    currentUser.setCurrentBalance(currentUser.getCurrentBalance().subtract(price));
                } else if (transactionType == TransactionType.OUT) {
                    currentUser.setCurrentBalance(currentUser.getCurrentBalance().add(price));
                }

                this.debitPurchaseRepository.delete(debitPurchaseToDelete);

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
