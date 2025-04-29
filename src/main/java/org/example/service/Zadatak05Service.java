package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.example.domen.User;

public class Zadatak05Service {

    public static void userLifeCycle() {

        EntityManager em;
        boolean isPersistent;
        Object userId;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("ljubica-pu")) {
            em = emf.createEntityManager();

            User user = new User();
            user.setFirstName("Ljubica");
            user.setLastName("Vlahovic");

            em.getTransaction().begin();
            em.persist(user); //user u persistent stanju
            em.getTransaction().commit(); //upisuje u bazu
            em.clear();

            user.setFirstName("promenjeno ime");
            try {
                em.getTransaction().begin();
                em.merge(user); //kad se u pdate radi koristi se merge
                em.getTransaction().commit();

                User newUser = em.getReference(User.class, user.getId());
                System.out.println("Novo ime: " + newUser.getFirstName() + " (trebalo bi da bude 'promenjeno ime'");
            } catch (Exception e) {
                e.printStackTrace();
                em.getTransaction().rollback();
            }

            em.getTransaction().begin();
            User merged = em.merge(user); //merged u persistent stanju
            em.getTransaction().commit();

            User proxy = em.getReference(User.class, merged.getId());
            em.detach(proxy); //pravimo ga
            try {
                proxy.setFirstName("bilosta");
                em.persist(proxy);
                System.err.println("DO OVOGA NE BI TREBALO DA DODJE!!!!");
            } catch (Exception exc) {
    //            exc.printStackTrace();
    //            System.out.println(exc.getMessage());
                System.out.println("Uspesno je doslo do greske prilikom detached proxya");
            }

            /// /// Napisati kod koji radi merge DETACHED objekta.

            proxy.setFirstName("ime");
            try {
                em.getTransaction().begin();
                em.merge(proxy);
                em.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
                em.getTransaction().rollback();
            }

            /// /// Napisati kod koji prikazuje kako radi find metoda.

            em.getTransaction().begin();
            User findUser = em.find(User.class, user.getId());
            em.getTransaction().commit();

            /// /// Napisati kod koji prikazuje da li je objekat u PERSISTANT stanju sa contains metodom

            User u1 = em.find(User.class, merged.getId());
            isPersistent = em.contains(u1);
            System.out.println("da li je user u persistent stanju " + isPersistent);
            em.detach(u1);
            boolean isStillPersistent = em.contains(u1);
            System.out.println("da li je user i dalje u persistent stanju? " + isStillPersistent);
//        em.close();

            /// /// Napisati kod koji prikazuje da li je objekat u TRANSIENT stanju isTransient = emf.getPersistenceUnitUtil().getIdentifier(user);
            User u2 = new User();
            userId = emf.getPersistenceUnitUtil().getIdentifier(u2);
        }
        boolean isTransient = userId == null;
        if (isTransient) {
            System.out.println("objekat je u trnsient stanju" + isTransient);
        } else {
            System.out.println("objekat nije u transient stanju: " + userId);
        }
//        emf.close();


        /// ///Napisati kod koji prikazuje da li je objekat u DETACHED stanju


        em.getTransaction().begin();
        User u3 = new User();
        u3.setFirstName("Ivan");
        u3.setLastName("Ilic");
        em.persist(u3);
        em.getTransaction().commit();

        em.clear();

        isPersistent = em.contains(u3);
        if (!isPersistent) {
            System.out.println("objekat je u detached stanju");
        } else {
            System.out.println("objekat nije u detached stanju");
        }
     //   em.close();
     //   emf.close();


        /// ///Napisati kod koji radi remove objekta

        User u4  = em.merge(u3);
        System.out.println("Da li je persistent nakon merge? -> " + em.contains(u4));

        em.remove(u4);
        System.out.println("Da li je persistent nakon brisanja? -> " + em.contains(u4));

        /// ///Napisati kod koji radi flush objekta manuelno, dakle da se vidi da se izvršava UPDATE prilikom flush poziva.

        User u5=new User();
        u5.setFirstName("Marko");
        u5.setLastName("Markovic");

        em.getTransaction().begin();
        u5.setFirstName("Mirko");
        em.persist(u5);
        System.out.println("Sace flush");
        em.flush(); //Flush mora transakcija
        System.out.println("flush gotov");
        em.getTransaction().commit();

        /// ///Napisati kod koji radi flush objekta preko Query metode, dakle da se vidi da se izvršava UPDATE prilikom flush poziva.
        /// Query query = em.createQuery("select u from User u");
        /// query.getResultList();//izvrsava se FLUSH, odnosno UPDATE u ovom slucaju

        User u6=new User();
        u6.setFirstName("Iva");
        u6.setLastName("Savic");
        em.persist(u6);

        u6.setFirstName("Ivana");
        System.out.println("sace flush");
        Query query=em.createQuery("select u from User u"); //automatski se radi flush prilikom select upita
        query.getResultList();                                      //da bi korisnik video azurne informacije
        System.out.println("evo ga flush");

    }



//        em.getTransaction().begin();
//        User u2=new User();
//        u2.setFirstName("Ana");
//        em.persist(u2);
//        em.flush();
//        em.getTransaction().commit();
//
//        em.getTransaction().begin();
//        Query query=em.createQuery("select u from User u");
//        query.getResultList();
//        em.getTransaction().commit();
//
//        em.close();
//        emf.close();

    public static void main(String[] args) {
        try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("ljubica-pu")) {
            System.out.println("A");
            System.out.println("O");
        }
    }

}








