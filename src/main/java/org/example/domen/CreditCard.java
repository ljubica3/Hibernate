package org.example.domen;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class CreditCard extends BillingDetails{

    private String number;
    private String expMonth;
    private String expYear;

    @OneToOne(fetch = FetchType.LAZY)
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CreditCard that = (CreditCard) o;
        return (id != null && that.id != null) ? Objects.equals(id, that.id) : number.equalsIgnoreCase(that.number);
    }

    @Override
    public int hashCode() {
        return (id != null) ? Objects.hashCode(id) : Objects.hashCode(number);
    }
}
