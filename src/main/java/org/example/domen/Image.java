package org.example.domen;

import jakarta.persistence.Embeddable;

@Embeddable
public class Image {

    public String name;
    public String title;

    public Image(){
    }

    public Image(String name, String title){
        this.name=name;
        this.title=title;
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
}
