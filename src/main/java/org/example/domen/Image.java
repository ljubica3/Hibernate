package org.example.domen;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    public Image(){
    }

    public Image(String name, String title){
        this.name=name;
        this.title=title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
