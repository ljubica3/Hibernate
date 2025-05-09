package org.example.service;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.example.domen.Bid;
import org.example.domen.Item;
import org.example.domen.User;
import org.example.util.HibernateUtil;
import org.hibernate.AssertionFailure;
import org.hibernate.Session;
import org.hibernate.transform.ToListResultTransformer;
import org.hibernate.transform.Transformers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Zadatak07QueryService {

    private EntityManagerFactory emf;

    public  Zadatak07QueryService() {
        emf = HibernateUtil.createEntityManagerFactoryWithData();
    }

        // 1.Napisati metodu queryKorisnici koja će izvršiti upit koji vraća sve korisnike.
        // Upit napisati na četiri načina - Query, TypedQuery, Criteria API, Native Query.

        public void queryKorisnici () {

            EntityManager em = emf.createEntityManager();

            List<User> korisnici = new ArrayList<>();
            //prvi nacin-Query
            Query query = em.createQuery("select u from User u");
            korisnici = query.getResultList();
            if (korisnici.size() != 10) throw new AssertionError("Ocekujemo 10 korisnika");

            //drugi nacin-TypedQuery
            TypedQuery<User> typedQuery = (TypedQuery<User>) em.createQuery("select u from User u");
            korisnici = typedQuery.getResultList();
            System.out.println("2 svi korisnici: " + korisnici);
            if (korisnici.size() != 10) throw new AssertionError("Ocekujemo 10 korisnika");
//
//            //treci nacin-Criteria API
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> u = cq.from(User.class);
            cq.select(u);
            korisnici = em.createQuery(cq).getResultList();
            System.out.println("3 svi korsinici: " + korisnici);
            if (korisnici.size() != 10) throw new AssertionError("Ocekujemo 10 korisnika");
//
//            //cetvrti nacin-Native Query
            Query nativeQuery = em.createNativeQuery("select * from Korisnik", User.class);
            korisnici = nativeQuery.getResultList();
            System.out.println("4 svi korisnici: " + korisnici);
            if (korisnici.size() != 10) throw new AssertionError("Ocekujemo 10 korisnika");

        }

        //2.Napisati metodu queryKorisniciRestriction koja će izvršiti upit koji vraca sve korisnike koji počinju na slovo N.
        // Upit napisati na 2 načina - TypedQuery i Criteria API.
        //Napisati zatim isti upit samo da bude case insensitive, dakle da vrati iste korisnike kada se pretražuje sa malim slovom n.

        public void queryKorisniciRestriction () {

            EntityManager em = emf.createEntityManager();

            //prvi nacin-TypedQuery
            TypedQuery<User> typedQuery = em.createQuery(" select u from User u where u.firstName like :ime", User.class);
            typedQuery.setParameter("ime", "N%");
            List<User> korisnici1 = typedQuery.getResultList();
            System.err.println("1 svi korisnici sa pocetnim slovom N:");
            System.out.println("Ukupno:" + korisnici1.size());

            for (User u : korisnici1) {
                if (!u.getFirstName().toLowerCase().startsWith("n")) throw new AssertionError("User ne pocinje sa N: " + u);
            }


            //drugi nacain-Criteria API
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> u = cq.from(User.class);
            cq.select(u).where(cb.like(u.get("firstName"), "N%"));
            List<User> korisnici2 = em.createQuery(cq).getResultList();

            //test
            for (User user : korisnici2) {
                if (!user.getFirstName().toLowerCase().startsWith("n")) throw new AssertionError("User ne pocinje sa N: " + u);
            }

        }

        //3.Napisati metodu queryKorisniciProjection koja će izvršiti upit koji vraća First i Last Name svih korisnika koji sadrže slovo 'i' u imenu i sortirati descending.
        // Upit napisati na 2 nacina - Query i Criteria API.

        public void queryKorisniciProjection () {
            EntityManager em = emf.createEntityManager();

            //prvi nacin-Query
            Query query = em.createQuery("select u.firstName, u.lastName from User u where u.firstName like :p1 order by u.firstName DESC", Object[].class);
            query.setParameter("p1", "%i%");
            /// kada vracamo samo neka polja vraca se niz objekata

            List<Object[]> rezultat1 = query.getResultList();

            for (Object[] red : rezultat1) {
                System.out.println("ime: " + red[0] + ", prezime: " + red[1]);
            }

            //drugi nacin-Criteria API
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<User> u = cq.from(User.class);

            cq.multiselect(u.get("firstName"), u.get("lastName"))
                    .where(cb.like(u.get("firstName"), "%i%"))
                    .orderBy(cb.asc(u.get("firstName")));

            List<Object[]> rezultati2 = em.createQuery(cq).getResultList();
            for (Object[] red : rezultati2) {
                System.out.println("Ime: " + red[0] + ", Prezime: " + red[1]);
            }
       }

            //4.Napisati metodu queryItemBidJoin koja će izvršiti upit koji spaja Item i Bid tabele i vratiti sve Bid redove
            // koji imaju Item čiji initialPrice je veći od 100.
            // Koristiti eksplicitni JOIN. Upit napisati na 2 načina - TypedQuery i Criteria API

        public void queryItemBidJoin () {
            EntityManager em = emf.createEntityManager();

            //prvi nacin-TypedQuery
            TypedQuery<Bid> typedQuery = em.createQuery(
                    "SELECT b FROM Bid b JOIN b.item i WHERE i.initialPrice> :cena", Bid.class);
            typedQuery.setParameter("cena", BigDecimal.valueOf(100));
            List<Bid> bids1 = typedQuery.getResultList();
            System.out.println("svi bid redovi cija je cena veca od 100");
            for (Bid b : bids1) System.out.println(b.getItem().getInitialPrice());

            //drugi nacin-Criteria API
            CriteriaBuilder cb=em.getCriteriaBuilder();
            CriteriaQuery<Bid> cq=cb.createQuery(Bid.class);
            Root<Bid> bidRoot=cq.from(Bid.class);
            Join<Bid, Item> itemJoin=bidRoot.join("item");

                cq.select(bidRoot)
                        .where(cb.gt(itemJoin.get("initialPrice"), BigDecimal.valueOf(100)));

                List<Bid> rez=em.createQuery(cq).getResultList();
            System.out.println("NACIN 2: bidovi cija je cena veca od 100: ");
            for (Bid b : rez) {
                System.out.println(b.getItem().getInitialPrice());
                if (b.getItem().getInitialPrice().compareTo(BigDecimal.valueOf(100)) < 0) throw new AssertionError("bla bla");
            }

        }

            //5.Napisati metodu queryItemTuple koja će izvršiti upit koji vraca sve iteme preko Tuple klase.
            //Upit napisati na 2 načina - TypedQuery i Criteria API.
            //Prva 2 upita treba da budu preko position tuple, dakle tuple.get(0)
            //Druga 2 upita treba da budu preko alias-a, dakle tuple.get(initialPrice).

        public void queryItemTuple() {
            EntityManager em = emf.createEntityManager();

            //1. TypedQuery-position
            TypedQuery<Tuple> typedQuery = em.createQuery("SELECT i.name, i.initialPrice FROM Item i", Tuple.class);
            List<Tuple> rez1 = typedQuery.getResultList();
            for (Tuple tuple : rez1) {
                String ime1 = tuple.get(0, String.class);
                BigDecimal cena1 = tuple.get(1, BigDecimal.class);
                System.out.println("ime: " + ime1 + "cena: " + cena1);
            }

            //2. Criteria API-position
            CriteriaBuilder cb=em.getCriteriaBuilder();
            CriteriaQuery<Tuple> criteriaQuery=cb.createTupleQuery();
            Root<Item> itemRoot=criteriaQuery.from(Item.class);
            criteriaQuery.multiselect(itemRoot.get("name"), itemRoot.get("initialPrice"));
            List<Tuple>rez2=em.createQuery(criteriaQuery).getResultList();
            for (Tuple tuple:rez2){
                String ime2=tuple.get(0,String.class);
                BigDecimal cena2=tuple.get(1,BigDecimal.class);
                System.out.println("ime: "+ime2+"cena: "+cena2);
            }

            //3. TypedQuery-alias
            TypedQuery<Tuple> typedQuery2=em.createQuery("SELECT i.name AS name, i.initialPrice AS initialPrice from Item i", Tuple.class);
            List<Tuple> rez3=typedQuery2.getResultList();
            for(Tuple tuple:rez3){
                String ime3=tuple.get("name", String.class);
                BigDecimal cena3=tuple.get("initialPrice", BigDecimal.class);
                System.out.println("ime: "+ime3+"cena: "+cena3);
            }

            //4. Criteia API-alias
            CriteriaQuery<Tuple> cq2=cb.createTupleQuery();
            Root<Item> itemRoot2=cq2.from(Item.class);
            cq2.multiselect(itemRoot2.get("name").alias("name"),
                    itemRoot2.get("initialPrice").alias("initialPrice"));
            List<Tuple>rez4= em.createQuery(cq2).getResultList();
            for(Tuple tuple:rez4){
                String ime4=tuple.get("name",String.class);
                BigDecimal cena4=tuple.get("initailPrice",BigDecimal.class);
                System.out.println("ime: "+ime4+"cena"+cena4);
            }

        }

            //6.Napisati metodu aliasToBeanResultTransformer koja će izvršiti upit koji vraća sve item-e
            // i automatski ih pretvara u Item objekat - AliasToBeanResultTransformer.
            //Hint: Ukoliko se desi ClassCastException u java.util.map (pogledati link  ) dodati aliase u select delu.

 //       public void aliasToBeanResultTransformer(){
//            EntityManager em = emf.createEntityManager();
//
//                List<Item> items = em.unwrap(Session.class)
//                        .createNativeQuery("SELECT i.id AS id, i.name AS name, i.initialPrice AS initialPrice from Item i")
//                        .setResultTransformer(Transformers.aliasToBean(Item.class)).list();
//
//                for (Item item : items) {
//                    System.out.println("ID: "+item.getId()+"name: "+item.getName()+"price: "+item.getInitialPrice());
//                }
//
//                /// PRESKOCENO JE JER JE DEPRECATED
            }



}