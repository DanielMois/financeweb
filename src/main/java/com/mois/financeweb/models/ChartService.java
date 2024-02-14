package com.mois.financeweb.models;

import com.mois.financeweb.repositories.CreditPurchaseRepository;
import com.mois.financeweb.repositories.DebitPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartService {

    @Autowired
    private final CreditPurchaseRepository creditPurchaseRepository;

    @Autowired
    private final DebitPurchaseRepository debitPurchaseRepository;

    public ChartService(CreditPurchaseRepository creditPurchaseRepository, DebitPurchaseRepository debitPurchaseRepository) {
        this.creditPurchaseRepository = creditPurchaseRepository;
        this.debitPurchaseRepository = debitPurchaseRepository;
    }

    public List<Object[]> getTotalPriceByTransactionType(Long userId) {
        return debitPurchaseRepository.findTotalPriceByTransactionType(userId);
    }

    public List<Object[]> getTotalPriceByInvoice(Long userId) {
        return creditPurchaseRepository.findTotalPriceByInvoice(userId);
    }

    public List<Object[]> cpGetTotalPriceByCategory(Long userId) {
        return creditPurchaseRepository.findTotalPriceByCategory(userId); }

    public List<Object[]> dpGetTotalPriceByCategory(Long userId) {
        return debitPurchaseRepository.findTotalPriceByCategory(userId); }



}
