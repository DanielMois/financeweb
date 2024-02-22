package com.mois.financeweb.models;

import com.mois.financeweb.dto.DebitPurchaseRequisition;
import com.mois.financeweb.repositories.DebitPurchaseRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DebitPurchaseService {

    @Autowired
    private DebitPurchaseRepository debitPurchaseRepository;

    @Autowired
    private HttpSession session;

    @Autowired
    private UserService userService;

    public DebitPurchase createDebitPurchase(@Valid DebitPurchaseRequisition req) {

        Long userId = userService.getCurrentUserId(session);
        User currentUser = userService.findById(userId);

        DebitPurchase debitPurchase = req.toDebitPurchase();
        debitPurchase.setUser(currentUser);

        BigDecimal price = debitPurchase.getPrice();
        TransactionType transactionType = debitPurchase.getTransactionType();

        if (transactionType == TransactionType.IN) {
            currentUser.setCurrentBalance(currentUser.getCurrentBalance().add(price));
        } else if (transactionType == TransactionType.OUT) {
            currentUser.setCurrentBalance(currentUser.getCurrentBalance().subtract(price));
        }

        this.debitPurchaseRepository.save(debitPurchase);

        return debitPurchase;

    }

    public DebitPurchase editDebitPurchase(DebitPurchase obsoleteDebitPurchase, DebitPurchaseRequisition req) {

        Long userId = userService.getCurrentUserId(session);

        User currentUser = userService.findById(userId);

        TransactionType oldTransactionType = obsoleteDebitPurchase.getTransactionType();
        BigDecimal oldPrice = obsoleteDebitPurchase.getPrice();

        DebitPurchase updatedDebitPurchase = req.toDebitPurchase(obsoleteDebitPurchase);

        TransactionType newTransactionType = updatedDebitPurchase.getTransactionType();
        BigDecimal newPrice = updatedDebitPurchase.getPrice();


        if (oldTransactionType == newTransactionType) {
            if (oldTransactionType == TransactionType.IN) {
                currentUser.setCurrentBalance(currentUser.getCurrentBalance().subtract(oldPrice).add(newPrice));
            } else if (oldTransactionType == TransactionType.OUT) {
                currentUser.setCurrentBalance(currentUser.getCurrentBalance().add(oldPrice).subtract(newPrice));
            }
        } else {
            if (oldTransactionType == TransactionType.IN) {
                currentUser.setCurrentBalance(currentUser.getCurrentBalance().subtract(oldPrice).subtract(newPrice));
            } else if (oldTransactionType == TransactionType.OUT) {
                currentUser.setCurrentBalance(currentUser.getCurrentBalance().add(oldPrice).add(newPrice));
            }
        }

        this.debitPurchaseRepository.save(updatedDebitPurchase);

        return updatedDebitPurchase;

    }

}
