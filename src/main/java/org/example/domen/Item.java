package org.example.domen;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private BigDecimal initialPrice;
    private LocalDate auctionEnd;

    @Version
    private Integer version;

    @ManyToMany
    @JoinTable(
            name = "category_item",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private static List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bid> bids = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private User seller;

    public Item() {
    }

    public Item(String name, BigDecimal initialPrice, LocalDate auctionEnd) {
        this.name = name;
        this.initialPrice = initialPrice;
        this.auctionEnd = auctionEnd;
    }


    public void addBid(Bid bid) {
        bids.add(bid);
        bid.setItem(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(BigDecimal initialPrice) {
        this.initialPrice = initialPrice;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LocalDate getAuctionEnd() {
        return auctionEnd;
    }

    public void setAuctionEnd(LocalDate auctionEnd) {
        this.auctionEnd = auctionEnd;
    }

    public void addCategory(Category category) {
        categories.add(category);
        category.getItems().add(this);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
        category.getItems().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Item{" +
                "Id=" + id +
                ", name='" + name + '\'' +
                ", initialPrice=" + initialPrice +
                ", user=" + user +
                ", seller=" + seller +
                '}';
    }

}