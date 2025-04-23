package org.example.domen;

import javax.persistence.*;
import java.util.List;

@Entity

public class User{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private Long id;
    private String identity;
    private String first_name;
    private String last_name;

    @OneToMany(mappedBy = "user")
    private List<Bid> bids;

    @Embedded
    private Address address;

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User(Long id, String identity, String first_name, String last_name, List<Bid> bids, Address adress) {
        this.id = id;
        this.identity = identity;
        this.first_name = first_name;
        this.last_name = last_name;
        this.bids = bids;
        this.address = adress;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", identity='" + identity + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", bids=" + bids +
                ", adress=" + address +
                '}';
    }
}
