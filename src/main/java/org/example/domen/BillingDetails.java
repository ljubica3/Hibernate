package org.example.domen;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class BillingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    protected String owner;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "owner_user")
        private User ownerUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
//
//        if (id != null && that.id != null) return Objects.equals(id, that.id);
//        return owner.equalsIgnoreCase(that.owner);

//        TERNARNI OPERATOR
        return (id != null && that.id != null) ? Objects.equals(id, that.id) : owner.equalsIgnoreCase(that.owner);

    }

    @Override
    public int hashCode() {
        return (id != null) ? Objects.hashCode(id) : Objects.hashCode(owner);
    }

    public static void main(String[] args) {
        Boolean nesto = false;
        String a = nesto ? "a" : "b";
        System.out.println(a);
    }
}




