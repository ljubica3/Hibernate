package org.example.domen;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class BillingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long Id;
    protected String owner;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "owner_user")
        private User ownerUser;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BillingDetails that = (BillingDetails) o;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Id);
    }
}




