package org.example.domen;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidder_id") //nema potrebe samo defi nise ime
    private User bidder;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    public Bid() {
    }

    public Bid(BigDecimal bigDecimal, Object o, Item fen) {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Bid bid = (Bid) o;
        return Double.compare(amount, bid.amount) == 0 && Objects.equals(id, bid.id) && Objects.equals(bidder, bid.bidder) && Objects.equals(item, bid.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, bidder, item);
    }
}


