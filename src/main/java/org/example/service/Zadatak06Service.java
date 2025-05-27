package org.example.service;

import jakarta.persistence.*;
import org.example.domen.Bid;
import org.example.domen.Item;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
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
            b1.setAmount(new BigDecimal(55)); //nacin 1
            Bid b2 = new Bid();
            b2.setItem(b2.getItem());
            b2.setAmount(BigDecimal.valueOf(475)); //nacin 2
            bids = Set.of(b1, b2);
            Item item = new Item();

            item.setName("ime");
//            item.setBids(bids);

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
            bid1.setAmount(new BigDecimal(445));

            em.persist(itemProxy);

            itemProxy.getBids().add(bid1);
            System.out.println("dodat je novi bid");


//          Napisati kod koji vraća Item, proverava da li je bids inicijalizovan preko PeristenceUtil klase i ispisuje u konzoli naziv klase za bids.
//          Treba da bude Collection Wrapper (PersistenceSet ili PersistenceBag u zavisnosti da li se koristi Set ili List).

            PersistenceUtil util = Persistence.getPersistenceUtil();
            boolean jeInicijalizovan = util.isLoaded(item.getBids(), "bids");

            System.err.println("INICIJALIZOVAN: " + jeInicijalizovan);

            Collection<Bid> bidss = item.getBids();

            System.err.println("NAZIV KLASE: " + bidss.getClass().getName());


            //Implementirati hashCode i equals metode u svim domenskim klasama.
            // Ukoliko imate BigDecimal klasu, koristiti se doubleValue i equals i hashcode metodama
            // jer BigDecimal ne radi ispravno - https://www.geeksforgeeks.org/bigdecimal-hashcode-method-in-java/
            //-Ukoliko je Bids kolekcija u Item klasi mapirana preko List interfejsa zameniti je sa Set interfejsom da bi testirali equals i hashcode.

            //po domenskim klasama


        }
    }

    void big(){
        Double vrednost;
        BigDecimal bigDecimal = new BigDecimal(5);
        bigDecimal = bigDecimal.add(new BigDecimal(10));
        //sout bigDEcimal => 15
    }
        //Uraditi persist preko Item klase. Rezultat treba da ostane isti u bazi.
        //Ispisati count bids objekata pre i posle dodavanja novig Bid objekat u kolekciju

        public static void dodajNoviBid() {

            EntityManager em;
            try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("ljubica-pu")) {
                em = emf.createEntityManager();
                em.getTransaction().begin();

                //
                Set<Bid> bids;
                Bid b1 = new Bid();
                b1.setItem(b1.getItem());
                b1.setAmount(new BigDecimal(55));
                Bid b2 = new Bid();
                b2.setItem(b2.getItem());
                b2.setAmount(new BigDecimal(475));
                Bid b3=new Bid();
                b3.setItem(b3.getItem());
                b3.setAmount(new BigDecimal(7501));

                Item item = new Item();
                item.setName("neki item");

                em.persist(item);
                em.getTransaction().commit();

//                Set<Bid> bidSet = Set.of(b1,b2,b3);
                Set<Bid> bidSet = new HashSet<>(Set.of(b1, b2, b3));

                item.setBids(bidSet);

//                long brojPre = em.createQuery("SELECT COUNT(b) FROM Bid b", Long.class).getSingleResult();
//                System.err.println("ukpan broj bidova pre: " + brojPre);
                Item item1 = em.find(Item.class, item.getId());
                System.err.println("1 ukpan broj bidova pre: " + item1.getBids().size());


                //Napisati kod koji dovlači Item objekat i dodaje novi Bid objekat sa istim vrednostima kao postojeći.
                em.getTransaction().begin();
                Item item4 = em.find(Item.class, item.getId());

                bids = item4.getBids();
                System.err.println("2 broj bidova je: " + bids.size());

                if (!bids.isEmpty()) {
                    Bid prviBid = bids.stream().findFirst().get();

                    Bid noviBid = new Bid();
                    noviBid.setAmount(prviBid.getAmount());
                    noviBid.setItem(prviBid.getItem());
                    noviBid.setBidder(prviBid.getBidder());
                    noviBid.setId(prviBid.getId());

                    bids.add(noviBid);
                    em.persist(item4);
                    em.getTransaction().commit();

                    System.err.println("3 trenutni broj bidova: " + bids.size()); //iako smo dodali novi element, on je isti, a ovo je set, pa se nije dodao
                }

                System.err.println("4 nema bidova");



                long brojPosle = em.createQuery("SELECT COUNT(b) FROM Bid b", Long.class).getSingleResult();
                System.err.println("5 broj bidova posel: " + brojPosle);
            }
        }}





