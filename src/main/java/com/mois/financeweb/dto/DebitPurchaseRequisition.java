package com.mois.financeweb.dto;

import com.mois.financeweb.models.Category;
import com.mois.financeweb.models.DebitPurchase;
import com.mois.financeweb.models.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class DebitPurchaseRequisition {
    @NotBlank
    @NotNull
    private String name;
    private Category category;

    private TransactionType transactionType;
    @NotNull
    @DecimalMin(value="0.0")
    private BigDecimal price;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public DebitPurchase toDebitPurchase() {
        DebitPurchase debitPurchase = new DebitPurchase();
        debitPurchase.setName(this.name);
        debitPurchase.setCategory(this.category);
        debitPurchase.setTransactionType(this.transactionType);
        debitPurchase.setPrice(this.price);

        return debitPurchase;
    }

    public DebitPurchase toDebitPurchase(DebitPurchase debitPurchase) {

        debitPurchase.setName(this.name);
        debitPurchase.setCategory(this.category);
        debitPurchase.setTransactionType(this.transactionType);
        debitPurchase.setPrice(this.price);

        return debitPurchase;
    }

    public void fromDebitPurchase(DebitPurchase debitPurchase) {
        this.transactionType = debitPurchase.getTransactionType();
        this.name = debitPurchase.getName();
        this.category = debitPurchase.getCategory();
        this.price = debitPurchase.getPrice();
    }

    @Override
    public String toString() {
        return "NewCreditPurchaseRequisition{" +
                ", name=" + name +
                ", category=" + category +
                ", transactionType=" + transactionType +
                ", price=" + price +
                "}";
    }
}