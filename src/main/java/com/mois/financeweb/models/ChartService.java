package com.mois.financeweb.models;

import com.mois.financeweb.repositories.CreditPurchaseRepository;
import com.mois.financeweb.repositories.DebitPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartService {

    @Autowired
    private CreditPurchaseRepository creditPurchaseRepository;

    @Autowired
    private DebitPurchaseRepository debitPurchaseRepository;

    public List<Object[]> getTotalPriceByTransactionType() {
        return debitPurchaseRepository.findTotalPriceByTransactionType();
    }

    public List<Object[]> getTotalPriceByInvoice() {
        return creditPurchaseRepository.findTotalPriceByInvoice();
    }

    public List<Object[]> cpGetTotalPriceByCategory() {return creditPurchaseRepository.findTotalPriceByCategory(); }

    public List<Object[]> dpGetTotalPriceByCategory() {return debitPurchaseRepository.findTotalPriceByCategory(); }



}
