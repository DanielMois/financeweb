package com.mois.financeweb.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class CreditPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String invoice;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;
    private BigDecimal price;
    private Date createDate;


    @ManyToOne
    private User user;

    public CreditPurchase(){}

    public CreditPurchase(String invoice, String name, Category category, BigDecimal price){
        this.invoice = invoice;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "CreditPurchase{" +
                "id=" + id +
                ", invoice=" + invoice +
                ", name=" + name +
                ", category=" + category +
                ", price=" + price +
                "}";

    }
}
