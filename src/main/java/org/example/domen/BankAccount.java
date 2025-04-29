package org.example.domen;

import jakarta.persistence.*;


@Entity
public class BankAccount extends BillingDetails{

    private String account;
    private String bankname;
    private String swift;

    @OneToOne
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
}
