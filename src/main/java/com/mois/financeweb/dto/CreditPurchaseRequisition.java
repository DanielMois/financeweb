package com.mois.financeweb.dto;

import com.mois.financeweb.models.Category;
import com.mois.financeweb.models.CreditPurchase;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreditPurchaseRequisition {

    private String invoice;
    @NotBlank
    @NotNull
    private String name;
    private Category category;

    @NotNull
    @DecimalMin(value="0.0")
    private BigDecimal price;

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CreditPurchase toCreditPurchase() {
        CreditPurchase creditPurchase = new CreditPurchase();
        creditPurchase.setName(this.name);
        creditPurchase.setCategory(this.category);
        creditPurchase.setPrice(this.price);

        return creditPurchase;
    }

    public CreditPurchase toCreditPurchase(CreditPurchase creditPurchase) {

        creditPurchase.setName(this.name);
        creditPurchase.setCategory(this.category);
        creditPurchase.setPrice(this.price);

        return creditPurchase;
    }

    public void fromCreditPurchase(CreditPurchase creditPurchase) {
        this.invoice = creditPurchase.getInvoice();
        this.name = creditPurchase.getName();
        this.category = creditPurchase.getCategory();
        this.price = creditPurchase.getPrice();
    }

    @Override
    public String toString() {
        return "NewCreditPurchaseRequisition{" +
                "invoice=" + invoice +
                ", name=" + name +
                ", category=" + category +
                ", price=" + price +
                "}";
    }
}