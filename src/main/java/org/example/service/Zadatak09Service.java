package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.domen.Bid;
import org.example.domen.Item;
import org.example.domen.User;
import org.example.util.HibernateUtil;


import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class Zadatak09Service {

    private static EntityManagerFactory emf= HibernateUtil.createEntityManagerFactory();
    private static EntityManager em=emf.createEntityManager();

    /// N + 1 Problem
//Napisati upit koji će da vrati sve Bids objekte.
// Zatim kroz petlju ispisati sve bids objekte u konzoli uključujući item od bid objekta.
// U konzoli kada se ide kroz petlju treba da se izvršavaju dodatni upiti iz item tabele.

    public static void vratiSveBidsSaItemima(){

            em.getTransaction().begin();

            Item item1 = new Item();
            item1.setName("item1");
            em.persist(item1);

            Item item2 = new Item();
            item2.setName("item2");
            em.persist(item2);

            Item item3 = new Item();
            item3.setName("item3");
            em.persist(item3);

            Bid b1 = new Bid();
            b1.setItem(item1);
            b1.setAmount(new BigDecimal(55));

            Bid b2 = new Bid();
            b2.setItem(item2);
            b2.setAmount(new BigDecimal(475));

            Bid b3 = new Bid();
            b3.setItem(item3);
            b3.setAmount(new BigDecimal(7501));

            em.persist(b1);
            em.persist(b2);
            em.persist(b3);

        em.getTransaction().commit();

        em.getTransaction().begin();
        List<Bid> bidovi=em.createQuery("select b from Bid b", Bid.class).getResultList();

        for(Bid b: bidovi){
            System.out.println("bid id: "+b.getId()+ ", amount: "+b.getAmount()+", item name:"+b.getItem().getName() );

        }
        em.getTransaction().commit();
    }

//Napisati zatim upit koji rešava ovaj problem i u jednom upitu vraća sve podatke.

    public static void vratiSveBidsSaItemimaBezProblema(){

        em.getTransaction().begin();

        Item item1 = new Item();
        item1.setName("item1");
        em.persist(item1);

        Item item2 = new Item();
        item2.setName("item2");
        em.persist(item2);

        Item item3 = new Item();
        item3.setName("item3");
        em.persist(item3);

        Bid b1 = new Bid();
        b1.setItem(item1);
        b1.setAmount(new BigDecimal(55));

        Bid b2 = new Bid();
        b2.setItem(item2);
        b2.setAmount(new BigDecimal(475));

        Bid b3 = new Bid();
        b3.setItem(item3);
        b3.setAmount(new BigDecimal(7501));

        em.persist(b1);
        em.persist(b2);
        em.persist(b3);

        em.getTransaction().commit();

        em.getTransaction().begin();

        List<Bid> bidovi=em.createQuery("select b from Bid b INNER JOIN b.item", Bid.class).getResultList();

        for(Bid b: bidovi){
            System.out.println("bid id: "+b.getId()+", amount: "+b.getAmount()+", item name: "+b.getItem().getName());
        }
        em.getTransaction().commit();
    }

    /// Batch Fetching
//Prilikom popunjavanja podataka dodati za svaki item seller vezu
//Dodati @BatchSize(size = 3) na User entitetu.
// Napisati upit u posebnom entity manageru koji vraća sve item-e i onda u petlji ispisuje seller-a za taj item.
// Treba da se desi da se u prvom ispisu item-a vrate 3 korisnika kroz jedan upit.

    public static void vratiSveItemeISellere(){

        em.getTransaction().begin();

        User seller1=new User();
        seller1.setFirstName("MARKO");
        seller1.setLastName("V");
        em.persist(seller1);

        User seller2=new User();
        seller2.setFirstName("Novica");
        seller2.setLastName("T");
        em.persist(seller2);

        User seller3=new User();
        seller3.setFirstName("lJUBICA");
        em.persist(seller3);

        Item i1=new Item();
        i1.setSeller(seller1);

        Item i2=new Item();
        i2.setSeller(seller2);

        Item i3=new Item();
        i3.setSeller(seller3);

        em.persist(i1);
        em.persist(i2);
        em.persist(i3);

        em.getTransaction().commit();

        em.getTransaction().begin();

        List<Item> items=em.createQuery("select i from Item i", Item.class).getResultList();

        for(Item i: items){
            System.out.println("Item: "+i.getName()+", Seller: "+i.getSeller().getFirstName());
        }em.getTransaction().commit();
    }

    /// Merge Detached Object
//Dodati CascadeType.MERGE na Item-User vezu.
// Učitati item objekat i zatvoriti em da bi postao detached.
// Izmeniti vrednost na Item i User entitetima i uraditi merge. 2 update naredbe treba da se dese.

    public static void mergeDetachedItem() {

        em.getTransaction().begin();

        User seller = new User();
        seller.setFirstName("ime1");
        seller.setLastName("prezime");
        em.persist(seller);

        Item item = new Item();
        item.setName("imeItem");
        item.setSeller(seller);
        em.persist(item);

        item = em.find(Item.class, 1L);
        item.getSeller().getFirstName();

        em.getTransaction().commit();
        em.close();

        item.setName("izmenjen naziv itema");
        item.getSeller().setFirstName("izmenjeni seller");
        EntityManager em2=emf.createEntityManager();
        em2.getTransaction().begin();

        Item mergedItem = new Item();
        mergedItem = em2.merge(item);

        em2.getTransaction().commit();
        em2.close();

        System.out.println("sacuvni izmenjeni podaci");

    }
}














