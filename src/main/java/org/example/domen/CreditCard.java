package org.example.domen;

import jakarta.persistence.*;

@Entity
public class CreditCard extends BillingDetails{

    private String number;
    private String expMonth;
    private String expYear;

    @OneToOne
    @JoinColumn(name = "user_id")
    private BillingDetails billingDetails ;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public BillingDetails getBillingDetails() {
        return billingDetails;
    }

    public void setBillingDetails(BillingDetails billingDetails) {
        this.billingDetails = billingDetails;
    }
}
