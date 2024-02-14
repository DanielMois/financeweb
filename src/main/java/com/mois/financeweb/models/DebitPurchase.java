package com.mois.financeweb.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class DebitPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;
    private TransactionType transactionType;
    private BigDecimal price;

    @ManyToOne
    private User user;


    public DebitPurchase(){}

    public DebitPurchase(TransactionType transactionType, String name, Category category, BigDecimal price){
        this.transactionType = transactionType;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public TransactionType getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CreditPurchase{" +
                "id=" + id +
                ", name=" + name +
                ", category=" + category +
                ", transactionType=" + transactionType +
                ", price=" + price +
                "}";

    }
}
