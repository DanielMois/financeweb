package com.mois.financeweb.repositories;

import com.mois.financeweb.models.CreditPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditPurchaseRepository extends JpaRepository<CreditPurchase, Long> {

    @Query("SELECT cp.invoice, SUM(cp.price) FROM CreditPurchase cp WHERE cp.user.id = :userId GROUP BY cp.invoice ORDER BY cp.invoice")
    List<Object[]> findTotalPriceByInvoice(@Param("userId") Long userId);

    @Query("SELECT cp.category, SUM(cp.price) FROM CreditPurchase cp WHERE cp.user.id = :userId GROUP BY cp.category ORDER BY cp.category")
    List<Object[]> findTotalPriceByCategory(@Param("userId") Long userId);

    List<CreditPurchase> findByUserId(Long userId);

}
