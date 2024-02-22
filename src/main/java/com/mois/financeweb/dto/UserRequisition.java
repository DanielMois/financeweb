package com.mois.financeweb.dto;

import com.mois.financeweb.models.User;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class UserRequisition {

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String login;

    @NotBlank
    @NotNull
    private String password;

    @NotNull
    @DecimalMin(value="0.0")
    private BigDecimal salary;

    @NotNull
    @DecimalMin(value="0.0")
    private BigDecimal firstBalance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getFirstBalance() {
        return firstBalance;
    }

    public void setFirstBalance(BigDecimal firstBalance) {
        this.firstBalance = firstBalance;
    }

    public User toUser() {

        User user = new User();
        user.setName(this.name);
        user.setLogin(this.login);
        user.setPassword(this.password);
        user.setSalary(this.salary);
        user.setFirstBalance(this.firstBalance);

        return user;
    }
}
