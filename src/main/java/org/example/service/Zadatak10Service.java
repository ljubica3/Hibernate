package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.RollbackException;
import org.example.domen.Item;
import org.example.domen.User;
import org.example.util.HibernateUtil;


import java.math.BigDecimal;

public class Zadatak10Service {

    static EntityManagerFactory emf = HibernateUtil.createEntityManagerFactory();

    /// Optimistic Locking and Versioning
    //Napraviti 2 paralelna thread-a da se izvrsavaju.
    // Prvi thread poziva find metodu, menja vrednost atributa i ceka 5s
    // Drugi thread dovlaci isti item i odmah mu menja i komituje vrednost
    // Prvi thread nastavlja dalje i kada uradi commit mora da se desi OptimisticLockException
    public static void dodajPodatke() {

        EntityManagerFactory emf = HibernateUtil.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Item item = em.find(Item.class, 1L);
        if (item == null) {
            User user = new User();
            user.setFirstName("Ljubica");
            user.setLastName("Vlahovic");
            em.persist(user);

            item = new Item();
            item.setName("Naocare");
            item.setSeller(user);
            item.setInitialPrice(BigDecimal.valueOf(5000));
            em.persist(item);
        }
        em.getTransaction().commit();
        em.close();
    }

    public static void optimisticLockVersioning() throws InterruptedException {


        Thread t1 = new Thread(() -> {
            System.err.println("POKRENUTA NIT 1");
            EntityManager em1 = emf.createEntityManager();

            em1.getTransaction().begin();

            Item item = em1.find(Item.class, 1L);
            item.setName("T1-izmenjeno");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                em1.getTransaction().commit();
                System.out.println("commit uspesan");
            } catch (OptimisticLockException | RollbackException e) {
                System.out.println("T1, optimistic lock: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception ex) {
                System.out.println("T1 - NEOCEKIVANA GRESKA!!!");
                ex.printStackTrace();
            }
                finally {
                em1.close();
                System.err.println("ZAVRSENA NIT 1");
            }
        });

        Thread t2 = new Thread(() -> {
            System.err.println("POKRENUTA NIT 2");
            EntityManager em2 = emf.createEntityManager();
            em2.getTransaction().begin();

            Item item = em2.find(Item.class, 1L);
            item.setName("T2-izmenjeno");

            em2.getTransaction().commit();
            System.out.println("commit uspesan");

            em2.close();
            System.err.println("ZAVRSENA NIT 2");

        });

        System.err.println("=== POKRECEM NITI!!! ===");

        t1.start();
        t2.start();

        System.err.println("=== SPAVAM ===");
        Thread.sleep(6000);
        System.err.println("=== BUDAN ===");


    }

    /// Isolation level
    //Na isti nacin napraviti drugu metodu
    // Prvi thread vraća sumu initialPrice svih item-a
    // Drugi thread menja vrednost nekog item-a
    // Prvi thread ponovo čita istu sumu
    // Rezultat upita u prvom thread-u mora da bude isti za oba query-ja jer MySql po defaultu ima REPEATABLE_READ isolation level
    public static void isolationLevel() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            EntityManager em1 = emf.createEntityManager();

            try {
                em1.getTransaction().begin();

                //znamo da ima samo jedan item
                BigDecimal suma1 = em1.find(Item.class, 1L).getInitialPrice();
                System.out.println("1 suma1: " + suma1);

                Thread.sleep(5000);

                BigDecimal suma2 = em1.find(Item.class, 1L).getInitialPrice();
                System.out.println("2 suma2: " + suma2);

                em1.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("1. greska: " + e.getMessage());
            } finally {
                em1.close();
            }

        });

        Thread t2 = new Thread(() -> {
            EntityManager em2 = emf.createEntityManager();
            try {
                Thread.sleep(2000);

                em2.getTransaction().begin();

                Item item = em2.find(Item.class, 1L);
                item.setInitialPrice(BigDecimal.valueOf(4500));

                em2.getTransaction().commit();
                System.out.println("promenjena cena itema 4500");


            } catch (Exception e) {
                System.out.println("2. greska: " + e.getMessage());
            } finally {
                em2.close();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

}





