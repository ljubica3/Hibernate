package org.example.domen;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)

    private Long Id;
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Item> items=new HashSet<>();

    @ManyToOne
    private Category partner;

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

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Category getPartner() {
        return partner;
    }

    public void setPartner(Category partner) {
        this.partner = partner;
    }
}
