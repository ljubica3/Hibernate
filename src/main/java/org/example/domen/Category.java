package org.example.domen;

import jakarta.persistence.*;

import java.util.*;

@Entity

public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long Id;
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Item> items=new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    public Category() {

    }
    public Category(String name) {
        this.name = name;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void addItem(Item item){
        items.add(item);
        item.getCategories().add(this);
    }

    public void removeItem(Item item){
        items.remove(item);
        item.getCategories().remove(this);
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
