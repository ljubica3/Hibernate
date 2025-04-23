package org.example;

import org.example.domen.Address;
import org.example.domen.Bid;
import org.example.domen.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("your-pu-name");
        EntityManager em = emf.createEntityManager();

        User user = new User();
        user.setIdentity("123");
        user.setFirst_name("Ljubica");
        user.setLast_name("Vlahovic");

        Address address = new Address();
        address.setStreet("Main St.");
        address.setZip_code("10000");
        address.setCity("Cityville");

        user.setAddress(address);

        Bid bid = new Bid();
        bid.setAmount(100.50);
        bid.setCreated_on(LocalDate.from(LocalDateTime.now()));
        bid.setUser(user);

        em.getTransaction().begin();

        em.persist(user);
        em.persist(bid);

        em.getTransaction().commit();

        User fetchedUser = em.find(User.class, user.getId());
        System.out.println(fetchedUser);

        em.close();
        emf.close();
    }
}