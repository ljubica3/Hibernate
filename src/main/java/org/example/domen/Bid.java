package org.example.domen;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class Bid {

    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)

    private Long id;
    private Double amount;
    private LocalDate created_on;

    @ManyToOne
    @JoinColumn(name="user_id")

    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getCreated_on() {
        return created_on;
    }

    public void setCreated_on(LocalDate created_on) {
        this.created_on = created_on;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", amount=" + amount +
                ", created_on=" + created_on +
                ", user=" + user +
                '}';
    }
}
