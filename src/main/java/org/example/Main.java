package org.example;

import org.example.domen.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("your-pu-name");
        EntityManager em = emf.createEntityManager();

        System.out.println("============== START ===============");

        User user = new User();
        user.setId(123L);
        user.setFirstName("Ljubica");
        user.setLastName("Vlahovic");

        Address address = new Address();
        address.setStreet("Main St.");
        address.setZipcode("10000");
        address.setCity("Cityville");

        user.setHomeAddress(address);

        Bid bid = new Bid();
        bid.setAmount(new BigDecimal("100.50"));

        em.getTransaction().begin();

        em.persist(user);
        em.persist(bid);

        em.getTransaction().commit();

        User fetchedUser = em.find(User.class, user.getId());
        System.out.println("FETCHED USER! " + fetchedUser);

        em.close();
        emf.close();
    }

    void asdf(){
        Item item = new Item();
        Image image = new Image();

        item.setImages(List.of(image));

        item.getImages().get(0);
    }
}