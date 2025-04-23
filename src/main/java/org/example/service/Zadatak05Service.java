package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.example.domen.User;

public class Zadatak05Service {

    public static void userLifeCycle() {

        EntityManagerFactory emf= Persistence.createEntityManagerFactory("zadatak05");
        EntityManager em=emf.createEntityManager();

        User user=new User();
        user.setFirstName("Ljubica");
        user.setLastName("Vlahovic");

        em.getTransaction().begin();
        em.persist(user); //user u persistent stanju
        em.getTransaction().commit(); //upisuje u bazu
        em.clear();

        user.setFirstName("promenjeno ime");
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }

        em.getTransaction().begin();
        User merged=em.merge(user); //merged u persistent stanju
        em.getTransaction().commit();

        User u1=em.find(User.class,merged.getId());

        User proxy = em.getReference(User.class,merged.getId());
        em.detach(proxy); //pravimo ga
        try{
            System.out.println(proxy.getFirstName());
        }catch (Exception exc){
            exc.printStackTrace();
        }

        System.out.println("contains: "+em.contains(merged)); //true

        User novi=new User();
        System.out.println("isTransient: " + emf.getPersistenceUnitUtil().getIdentifier(user));

        em.getTransaction().begin();
        em.remove(em.contains(merged) ? merged : em.merge(merged));
        em.getTransaction().commit();

        em.getTransaction().begin();
        User u2=new User();
        u2.setFirstName("Ana");
        em.persist(u2);
        em.flush();
        em.getTransaction().commit();

        em.getTransaction().begin();
        Query query=em.createQuery("select u from User u");
        query.getResultList();
        em.getTransaction().commit();

        em.close();
        emf.close();











    }

}
