package com.mois.financeweb.repositories;

import com.mois.financeweb.models.CreditPurchase;
import com.mois.financeweb.models.DebitPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebitPurchaseRepository extends JpaRepository<DebitPurchase, Long> {

    @Query("SELECT dp.transactionType, SUM(dp.price) FROM DebitPurchase dp GROUP BY dp.transactionType ORDER BY dp.transactionType")
    List<Object[]> findTotalPriceByTransactionType();

    @Query("SELECT dp.category, SUM(dp.price) FROM DebitPurchase dp GROUP BY dp.category ORDER BY dp.category")
    List<Object[]> findTotalPriceByCategory();

}
