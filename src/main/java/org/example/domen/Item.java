package org.example.domen;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;

    @ManyToMany
    @JoinTable(
            name="category_item",
            joinColumns = @JoinColumn(name="item_id"),
            inverseJoinColumns = @JoinColumn(name="category_id")
    )
    private Set<Category> categories=new HashSet<>();

    @OneToMany(mappedBy = "item")
    private List<Image> images=new ArrayList<>();

    @OneToMany(mappedBy="item", cascade = CascadeType.ALL)
    private Set<Bid> bids = new HashSet<>();

    @ManyToOne
    private User user;

    @ManyToOne
    private User seller;

    public void addBid(Bid bid){
        bids.add(bid);
        bid.setItem(this);
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(Id, item.Id) && Objects.equals(name, item.name) && Objects.equals(categories, item.categories) && Objects.equals(images, item.images) && Objects.equals(bids, item.bids) && Objects.equals(user, item.user) && Objects.equals(seller, item.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, categories, images, bids, user, seller);
    }
}
