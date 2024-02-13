package com.mois.financeweb.repositories;

import com.mois.financeweb.models.CreditPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditPurchaseRepository extends JpaRepository<CreditPurchase, Long> {

    @Query("SELECT cp.invoice, SUM(cp.price) FROM CreditPurchase cp GROUP BY cp.invoice ORDER BY cp.invoice")
    List<Object[]> findTotalPriceByInvoice();

    @Query("SELECT cp.category, SUM(cp.price) FROM CreditPurchase cp GROUP BY cp.category ORDER BY cp.category")
    List<Object[]> findTotalPriceByCategory();

}
