package org.example.domen;

import jakarta.persistence.*;

@Entity
public class BillingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String postalCode;
    private String city;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    public BillingAddress() {
    }

    public BillingAddress(String street, String postalCode, String city) {
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
