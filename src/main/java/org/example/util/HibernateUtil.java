package org.example.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.domen.Address;
import org.example.domen.Bid;
import org.example.domen.Item;
import org.example.domen.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

public class HibernateUtil {

    public static EntityManagerFactory createEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("ljubica-pu");
    }

    public static EntityManagerFactory createEntityManagerFactoryWithData() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ljubica-pu");
        popuniDemoPodatke(emf);
        return emf;

    }

    private static void popuniDemoPodatke(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Address adresaNinovic = new Address("Evropska 13", "11070", "Beograd");
        User ninovic = new User("n.ninovic", "Nikola", "Ninovic", adresaNinovic, adresaNinovic);
        User dimitrijevc = new User("a.dimitrijevic", "Ana", "Dimitrijevic", adresaNinovic, adresaNinovic);
        User stankovic = new User("n.stankovic", "Nikola", "Stankovic", adresaNinovic, adresaNinovic);
        User dobricic = new User("s.dobricic", "Stefan", "Dobricic", adresaNinovic, adresaNinovic);
        User stojanovic = new User("m.stojanovic", "Milan", "Stojanovic", adresaNinovic, adresaNinovic);
        User dedovic = new User("a.dedovic", "Ana", "Dedovic", adresaNinovic, adresaNinovic);
        User jovicic = new User("r.jovicic", "Radenko", "Jovicic", adresaNinovic, adresaNinovic);
        User milic = new User("m.milic", "Marko", "Milic", adresaNinovic, adresaNinovic);
        User miljevic = new User("m.miljevic", "Manja", "Miljevic", adresaNinovic, adresaNinovic);
        User srdic = new User("i.srdic", "Ivan", "Srdic", adresaNinovic, adresaNinovic);
        Stream<User> users = Stream.of(ninovic, dimitrijevc, stankovic, dobricic, stojanovic, dedovic, jovicic, milic, miljevic, srdic);
        users.forEach(em::persist);

        Item televizor = new Item("Televizor", BigDecimal.valueOf(200), LocalDate.of(2020, Month.APRIL, 20));
        Item fen = new Item("Fen", BigDecimal.valueOf(33), LocalDate.of(2020, Month.APRIL, 21));
        Item auto = new Item("Auto", BigDecimal.valueOf(1700), LocalDate.of(2020, Month.APRIL, 27));
        Item sesir = new Item("Sesir", BigDecimal.valueOf(15), LocalDate.of(2020, Month.MARCH, 10));
        Item pracka = new Item("Pracka", BigDecimal.valueOf(5), LocalDate.of(2020, Month.FEBRUARY, 20));
        Item zagor = new Item("Zagor", BigDecimal.valueOf(29), LocalDate.of(2020, Month.JANUARY, 20));
        Stream<Item> items = Stream.of(televizor, fen, auto, sesir, pracka, zagor);
        items.forEach(em::persist);

        Bid bidTelevizor1 = new Bid(BigDecimal.valueOf(200), null, televizor);
        Bid bidTelevizor2 = new Bid(BigDecimal.valueOf(250), LocalDate.now(), televizor);
        Bid bidFen1 = new Bid(BigDecimal.valueOf(35), null, fen);
        Bid bidFen2 = new Bid(BigDecimal.valueOf(40), null, fen);
        Bid bidFen3 = new Bid(BigDecimal.valueOf(50), LocalDate.now(), fen);
        Bid bidAuto1 = new Bid(BigDecimal.valueOf(1700), LocalDate.now(), auto);
        Bid bidPracka1 = new Bid(BigDecimal.valueOf(6), null, pracka);
        Bid bidPracka2 = new Bid(BigDecimal.valueOf(7), null, pracka);
        Bid bidPracka3 = new Bid(BigDecimal.valueOf(8), null, pracka);
        Bid bidPracka4 = new Bid(BigDecimal.valueOf(9),LocalDate.now(), pracka);
        Bid bidZagor1 = new Bid(BigDecimal.valueOf(30), null, zagor);
        Bid bidZagor2 = new Bid(BigDecimal.valueOf(40), null, zagor);
        Stream<Bid> bids = Stream.of(bidTelevizor1, bidTelevizor2, bidFen1, bidFen2, bidFen3, bidAuto1, bidPracka1, bidPracka2, bidPracka3, bidPracka4, bidZagor1, bidZagor2);
        bids.forEach(em::persist);
        tx.commit();
        em.close();
    }

    private static final SessionFactory sessionFactory=buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

}

