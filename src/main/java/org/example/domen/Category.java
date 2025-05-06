package org.example.domen;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity

public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long Id;
    private String name;

    @ManyToMany(mappedBy = "categories",fetch = FetchType.LAZY)
    private Set<Item> items=new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

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
        return parent;
    }

    public void setPartner(Category partner) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(Id, category.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Id);
    }
}
