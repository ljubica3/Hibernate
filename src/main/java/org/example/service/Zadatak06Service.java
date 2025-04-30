package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUtil;
import org.example.domen.Bid;
import org.example.domen.Item;
import org.example.domen.User;
import org.hibernate.Hibernate;

import java.util.Collection;
import java.util.Set;


public class Zadatak06Service {


    public static void checkFatchStrategy() {
        EntityManager em;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("ljubica-pu")) {
            em = emf.createEntityManager();
//            em.getTransaction().begin();

            /// ///Dodati metodu koja će da radi sa fetch tipovima: checkFetchStrategy.
            /// Vratiti iz baze Item objekat. Uraditi detach i ispisati na konzoli broj bids objekata.
            /// Da bi to radilo potrebno je bids inicijalizovati. Potrebno je inicijalizaciju uraditi na 2 načina.


        Set<Bid> bids;
        Bid b1 = new Bid();
        b1.setItem(b1.getItem());
        b1.setAmount(55);
        Bid b2 = new Bid();
        b2.setItem(b2.getItem());
        b2.setAmount(475);
        bids = Set.of(b1, b2);
        Item item=new Item();

        item.setName("ime");
        item.setBids(bids);

        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();

        Item newItem = em.find(Item.class, 1L);

        em.detach(newItem);

        System.err.println("DETACH JE OBAVLJEN");
        int brojBids = newItem.getBids().size();
        System.err.println("broj bids objekata: " + brojBids);

        Item item2 = em.find(Item.class, item.getId());
        Hibernate.initialize(item2.getBids());

        System.err.println("broj bids objekata2: " + item2.getBids().size());

       /// ///Napisati kod u kome će se vratiti Item objekat i onda pozvati metodu koja proverava da li je objekat inicijalizovan

        Item item3 = em.find(Item.class, item.getId());

        boolean isInicijalizovan = Persistence.getPersistenceUtil().isLoaded(item3.getBids());
        System.out.println("da li je objekat inizijalizovan? " + isInicijalizovan);

        /// ///Dodati novi Bid za Item koristeći samo proxy za Item - getReference metoda

       Item itemProxy = em.getReference(Item.class, item.getId());

       Bid bid1 = new Bid();
       bid1.setItem(itemProxy);
       bid1.setAmount(445);

       em.persist(itemProxy);

      itemProxy.getBids().add(bid1);
            System.out.println("dodat je novi bid");


//          Napisati kod koji vraća Item, proverava da li je bids inicijalizovan preko PeristenceUtil klase i ispisuje u konzoli naziv klase za bids.
//          Treba da bude Collection Wrapper (PersistenceSet ili PersistenceBag u zavisnosti da li se koristi Set ili List).

            PersistenceUtil util= Persistence.getPersistenceUtil();
            boolean jeInicijalizovan = util.isLoaded(item.getBids(), "bids");

            System.err.println("INICIJALIZOVAN: "+jeInicijalizovan);

            Collection<Bid> bidss = item.getBids();

            System.err.println("NAZIV KLASE: " + bidss.getClass().getName());


            //  em.getTransaction().commit();

        }

        }

}

