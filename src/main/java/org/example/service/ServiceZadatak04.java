package org.example.service;

import org.example.domen.*;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;

public class ServiceZadatak04 {

    public void saveItem(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction trans=session.beginTransaction();

        Category c1 = new Category();
        c1.setName("kategorija a");

        Category c2 = new Category();
        c2.setName("kategorija b");

        session.persist(c1);
        session.persist(c2);

        for (int i = 1; i <= 3; i++) {
            Item item = new Item();
            item.setName("Item " + i);

            Image img1 = new Image("slika" + i + "_1.jpg", "Slika 1");
            Image img2 = new Image("slika" + i + "_2.jpg", "Slika 2");

            item.setImages(Arrays.asList(img1, img2));

            item.getCategories().add(c1);
            item.getCategories().add(c2);

            c1.getItems().add(item);
            c2.getItems().add(item);

            session.persist(item);
        }

        trans.commit();
        session.close();
    }

    public void createBid(Long itemId, Long bidderId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Item item = session.get(Item.class, itemId);
        User bidder = session.get(User.class, bidderId);

        Bid bid = new Bid();
        bid.setAmount(100.0);
        bid.setBidder(bidder);

        item.addBid(bid); // Bid se dodaje preko inverse strane

        session.merge(item); // Pošto menjamo inverse stranu

        tx.commit();
        session.close();
    }

    public void addSeller(Long itemId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();

        User user = new User();
        user.setUsername("prodavac123");

        session.persist(user);

        Item item = session.get(Item.class, itemId);
        item.setSeller(user);

        session.merge(item);

        trans.commit();
        session.close();
    }

    public Long createUserWithAddresses() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        User user = new User();
        user.setUsername("kupac456");

        Address home = new Address();
        home.setStreet("Ulica 1");
        home.setCity("Grad");
        home.setZipcode("11000");

        Address shipping = new Address();
        shipping.setStreet("Ulica 2");
        shipping.setCity("Grad");
        shipping.setZipcode("11100");

        user.setHomeAddress(home);
        user.setShippingAddress(shipping);

        session.persist(user);

        tx.commit();
        session.close();

        return user.getId(); // Potrebno za povezivanje sa kredit karticom
    }

    public void addCreditCard(Long userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();

        User user = session.get(User.class, userId);

        CreditCard cc = new CreditCard();
        cc.setNumber("1234-5678-9012-3456");
        cc.setExpiry("12/30");
        cc.setUser(user);

        user.setCreditCard(cc);

        session.merge(user);

        trans.commit();
        session.close();
    }
}

