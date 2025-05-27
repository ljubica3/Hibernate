package org.example.domen;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Korisnik")
@BatchSize(size=3)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String firstName;
    private String lastName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="street", column=@Column(name="home_street")),
            @AttributeOverride(name="city", column=@Column(name="home_city")),
            @AttributeOverride(name="zipcode", column = @Column(name="home_zip"))
    })
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="street", column = @Column(name="shipping_street")),
            @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "shipping_zip"))
    })
    private Address shippingAddress;

    @AttributeOverrides({
            @AttributeOverride(name="street", column = @Column(name="billing_street")),
            @AttributeOverride(name = "city", column = @Column(name = "billing_city")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "billing_zip"))
    })
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY,optional = false)
    private BillingAddress billingAddress;

    @OneToMany(mappedBy = "ownerUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillingDetails> billingDetails = new ArrayList<>();

    public User() {
    }

    public User(String username, String firstName, String lastName, Address homeAddress, Address shippingAddress) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.homeAddress = homeAddress;
        this.shippingAddress = shippingAddress;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(BillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<BillingDetails> getBillingDetails() {
        return billingDetails;
    }

    public void setBillingDetails(List<BillingDetails> billingDetails) {
        this.billingDetails = billingDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User that = (User) o;
  //      return Objects.equals(id, user.id);


        return (id != null && that.id != null) ? Objects.equals(id, that.id) : username.equalsIgnoreCase(that.username);

    }

    @Override
    public int hashCode() {

        return (id != null) ? Objects.hashCode(id) : Objects.hashCode(username);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", homeAddress=" + homeAddress +
                ", shippingAddress=" + shippingAddress +
                ", billingAddress=" + billingAddress +
                ", billingDetails=" + billingDetails +
                '}';
    }

    public void addBillingDetails(BillingDetails bd) {
        billingDetails.add(bd);
        bd.setOwnerUser(this);
    }

}
