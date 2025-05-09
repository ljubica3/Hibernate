package org.example.domen;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private LocalDate createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidder_id") //nema potrebe samo defi nise ime
    private User bidder;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    public Bid() {
    }

    public Bid(BigDecimal amount, LocalDate createdOn, Item item) {
        this.amount = amount;
        this.createdOn = createdOn;
        this.item = item;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Bid bid = (Bid) o;
        return amount.compareTo(bid.amount) == 0 && Objects.equals(id, bid.id) && Objects.equals(bidder, bid.bidder) && Objects.equals(item, bid.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, bidder, item);
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", amount=" + amount +
                ", bidder=" + bidder +
                ", item=" + item +
                '}';
    }
}


