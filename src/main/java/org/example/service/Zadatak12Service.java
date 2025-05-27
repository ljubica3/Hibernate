package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.domen.*;
import org.example.util.HibernateUtil;

import java.math.BigDecimal;

public class Zadatak12Service {

    private static EntityManagerFactory emf= HibernateUtil.createEntityManagerFactory();
    private static EntityManager em=emf.createEntityManager();

    //Dodati novi entitet, BillingAddress, i napraviti OneToOne vezu na način koji se preporučuje u lekciji.
    //BillingAddress ima atribute street, postalCode i City.
    //Kreirati User sa BillingAddress entitetom i kroz jedan persist sačuvati oba objekta.

    public static void kreirajUseraSaBillingAddress(){

        em.getTransaction().begin();

        BillingAddress address=new BillingAddress("Vojvode Stepe 36","11010","Beograd");
        User user=new User();
        user.setUsername("Aleksa");
        user.setBillingAddress(address);

        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    //Napraviti OneToMany unidirekcionu vezu Item - Bid.
    //Kreirati jedan Item sa 2 bid reda.
    //U upitima koji se generišu treba videti probleme navedene u lekciji.
    //Može da se napravi sa ili bez JoinColumn.

    public static void kreirajItemSaBid(){

        em.getTransaction().begin();

        Bid bid1 = new Bid();
        Bid bid2 = new Bid();
        bid1.setItem(bid1.getItem());
        bid1.setAmount(new BigDecimal(500));
        bid2.setItem(bid2.getItem());
        bid2.setAmount(new BigDecimal(4504));
        em.persist(bid1);
        em.persist(bid2);

        Item item=new Item();
        item.setName("Monitor");

        item.addBid(bid1);
        item.addBid(bid2);

        em.persist(item);
        em.getTransaction().commit();
    }

    //Napraviti CategoryItem ManyToMany vezu koristeći List interfejs i prikazati preko upita probleme navedene u lekciji.

    public static void poveziIteme(){

        em.getTransaction().begin();

        Category elektronika=new Category("elektronika");
        Category nesto=new Category("nesto");

        Item televizor=em.createQuery("select i from Item i where i.name=:name",Item.class).setParameter("name","nesto").getSingleResult();
        Item gumica=em.createQuery("select i from Item i where name=:name",Item.class).setParameter("name","gumica").getSingleResult();

        televizor.addCategory(elektronika);
        gumica.addCategory(nesto);

        em.persist(elektronika);
        em.persist(nesto);

        em.getTransaction().commit();
        em.close();

    }

    public static void prikaziProblemManyToMany(){

        em.getTransaction().begin();

        Item tv=em.createQuery("select i from Item i where i.name=:name", Item.class).setParameter("name","tv").getSingleResult();

        Category novaKategorija=new Category("nova kategorija");
        tv.addCategory(novaKategorija);

        em.persist(novaKategorija);
        em.getTransaction().commit();
        em.close();
    }

}
