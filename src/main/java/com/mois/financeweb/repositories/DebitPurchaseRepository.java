package com.mois.financeweb.repositories;

import com.mois.financeweb.models.DebitPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebitPurchaseRepository extends JpaRepository<DebitPurchase, Long> {

    @Query("SELECT dp.transactionType, SUM(dp.price) FROM DebitPurchase dp WHERE dp.user.id = :userId GROUP BY dp.transactionType ORDER BY dp.transactionType")
    List<Object[]> findTotalPriceByTransactionType(@Param("userId") Long userId);

    @Query("SELECT dp.category, SUM(dp.price) FROM DebitPurchase dp WHERE dp.user.id = :userId GROUP BY dp.category ORDER BY dp.category")
    List<Object[]> findTotalPriceByCategory(@Param("userId") Long userId);

    List<DebitPurchase> findByUserId(Long userId);

}
