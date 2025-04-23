package org.example.domen;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String street;
    private String zip_code;
    private String city;

    public Address(String street, String city, String zipCode) {
        this.street = street;
        this.city = city;
        this.zip_code= zip_code;
    }

    public Address() {

    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", zip_code='" + zip_code + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

