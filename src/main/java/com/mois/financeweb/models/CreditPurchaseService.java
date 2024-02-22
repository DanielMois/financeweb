package com.mois.financeweb.models;

import com.mois.financeweb.dto.CreditPurchaseRequisition;
import com.mois.financeweb.repositories.CreditPurchaseRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CreditPurchaseService {

    @Autowired
    private CreditPurchaseRepository creditPurchaseRepository;

    @Autowired
    private HttpSession session;

    @Autowired
    private UserService userService;

    public CreditPurchase createCreditPurchase(@Valid CreditPurchaseRequisition req) {
        Long userId = userService.getCurrentUserId(session);
        User currentUser = userService.findById(userId);

        CreditPurchase creditPurchase = req.toCreditPurchase();
        creditPurchase.setUser(currentUser);
        creditPurchase.setCreateDate(new Date());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");

        creditPurchase.setInvoice(dateFormat.format(creditPurchase.getCreateDate()));
        this.creditPurchaseRepository.save(creditPurchase);

        return creditPurchase;
    }

    public CreditPurchase editCreditPurchase(CreditPurchase obsoleteCreditPurchase, CreditPurchaseRequisition req) {

        CreditPurchase updatedCreditPurchase = req.toCreditPurchase(obsoleteCreditPurchase);

        updatedCreditPurchase.setInvoice(obsoleteCreditPurchase.getInvoice());
        this.creditPurchaseRepository.save(updatedCreditPurchase);

        return updatedCreditPurchase;

    }
}
