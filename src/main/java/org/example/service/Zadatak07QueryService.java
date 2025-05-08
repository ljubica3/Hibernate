package org.example.service;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.domen.Bid;
import org.example.domen.User;
import org.example.util.HibernateUtil;

import java.math.BigDecimal;
import java.util.List;

public class Zadatak07QueryService {

    private EntityManagerFactory emf;
   // EntityManager em = emf.createEntityManager();

    public void Zadatak07Service() {
   //     emf = HibernateUtil.createEntityManagerFactory();
    }

    // 1.Napisati metodu queryKorisnici koja će izvršiti upit koji vraća sve korisnike.
    // Upit napisati na četiri načina - Query, TypedQuery, Criteria API, Native Query.

        public void queryKorisnici () {

        EntityManager em=emf.createEntityManager();
        emf = HibernateUtil.createEntityManagerFactory();

            //prvi nacin-Query
            Query query = em.createQuery("select u from User u");
            List<User> korisnici1 = query.getResultList();
            System.out.println("1 svi korisnici: " + korisnici1);

            //drugi nacin-TypedQuery
            TypedQuery<User> typedQuery= (TypedQuery<User>) em.createQuery("select u from User u");
            List<User> korisnici2=typedQuery.getResultList();
            System.out.println("2 svi korisnici: "+korisnici2);

            //treci nacin-Criteria API
            CriteriaBuilder cb=em.getCriteriaBuilder();
            CriteriaQuery<User> cq=cb.createQuery(User.class);
            Root<User> u=cq.from(User.class);
            cq.select(u);
            List<User> korisnici3=em.createQuery(cq).getResultList();
            System.out.println("3 svi korsinici: "+korisnici3);

            //cetvrti nacin-Native Query
            Query nativeQuery=em.createNativeQuery("select u from User u", User.class);
            List<User> korisnici4=nativeQuery.getResultList();
            System.out.println("4 svi korisnici: "+korisnici4);

    }

    //2.Napisati metodu queryKorisniciRestriction koja će izvršiti upit koji vraca sve korisnike koji počinju na slovo N.
    // Upit napisati na 2 načina - TypedQuery i Criteria API.
    //Napisati zatim isti upit samo da bude case insensitive, dakle da vrati iste korisnike kada se pretražuje sa malim slovom n.

        public void queryKorisniciRestriction(){

            EntityManager em=emf.createEntityManager();
            emf = HibernateUtil.createEntityManagerFactory();
        //prvi nacin-TypedQuery
            TypedQuery<User> typedQuery=em.createQuery(" select u from User u where u.firstName like :ime", User.class);
            typedQuery.setParameter("ime","%N");
            List<User> korisnici1=typedQuery.getResultList();
            System.out.println("1 svi korisnici sa pocetnim slovom N: "+korisnici1);

        //drugi nacain-Criteria API
            CriteriaBuilder cb=em.getCriteriaBuilder();
            CriteriaQuery<User> cq=cb.createQuery(User.class);
            Root<User> u=cq.from(User.class);
            cq.select(u).where(cb.like(u.get("firstName"),"%N"));
            List<User>korisnici2=em.createQuery(cq).getResultList();
            System.out.println("2 svi korisnici sa pocetnim slovom N: "+korisnici2);

        }

    //3.Napisati metodu queryKorisniciProjection koja će izvršiti upit koji vraća First i Last Name svih korisnika koji sadrže slovo 'i' u imenu i sortirati descending.
    // Upit napisati na 2 nacina - Query i Criteria API.

        public void queryKorisniciProjection(){
            EntityManager em=emf.createEntityManager();
            emf = HibernateUtil.createEntityManagerFactory();
        //prvi nacin-Query
            Query query=em.createQuery("select u from User u where u.firstName like :ime AND u.lastName like :prezime order by u.firstName DESC" , User.class);
            query.setParameter("ime","%i%");

            List<Object[]>rezultat1=query.getResultList();

            for (Object[] red : rezultat1) {
              System.out.println("ime: " + red[0] + "prezime: " + red[1]);
          }

        //drugi nacin-Criteria API
            CriteriaBuilder cb=em.getCriteriaBuilder();
            CriteriaQuery<Object[]>cq=cb.createQuery(Object[].class);
            Root<User> u=cq.from(User.class);

            cq.multiselect(u.get("firstName"),u.get("lastName"))
                    .where(cb.like(u.get("firstName"), "%i%"))
                    .orderBy(cb.desc(u.get("firstName")));

            List<Object[]> rezultati2 = em.createQuery(cq).getResultList();
            for (Object[] red : rezultati2) {
                System.out.println("Ime: " + red[0] + ", Prezime: " + red[1]);
            }
        }

        //4.Napisati metodu queryItemBidJoin koja će izvršiti upit koji spaja Item i Bid tabele i vratiti sve Bid redove
        // koji imaju Item čiji initialPrice je veći od 100.
        // Koristiti eksplicitni JOIN. Upit napisati na 2 načina - TypedQuery i Criteria API

        public void queryItemBidJoin(){
            EntityManager em=emf.createEntityManager();
            emf = HibernateUtil.createEntityManagerFactory();

            TypedQuery<Bid> typedQuery = em.createQuery(
                    "SELECT b FROM Bid b JOIN b.item i WHERE i.initialPrice>100 :cena", Bid.class);
            typedQuery.setParameter("cena", BigDecimal.valueOf(100));
            List<Bid> bids1 = typedQuery.getResultList();
            System.out.println("svi bid redovi ciji je initialPrice veci od 100"+bids1);


        }




}