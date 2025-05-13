package org.example.domen;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
public class BankAccount extends BillingDetails {

    private String account;
    private String bankname;
    private String swift;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private BillingDetails billingDetails;

    public void setAccount(String account) {
        this.account = account;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public void setBillingDetails(BillingDetails billingDetails) {
        this.billingDetails = billingDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BankAccount that = (BankAccount) o;
        return (id!=null && that.id !=null) ? Objects.equals(id, that.id) : account.equalsIgnoreCase(that.account);

    }

    @Override
    public int hashCode() {
        return (id != null) ? Objects.hashCode(id) : Objects.hashCode(account);
    }

}
