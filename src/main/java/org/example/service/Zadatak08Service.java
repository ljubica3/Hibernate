package org.example.service;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.domen.BankAccount;
import org.example.domen.BillingDetails;
import org.example.domen.CreditCard;
import org.example.domen.User;
import org.example.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class Zadatak08Service {

    private static EntityManagerFactory emf;

    public Zadatak08Service(EntityManager em) {
    }


    public void kreirajKorisnika(EntityManager em) {


        User korisnik1 = new User();
        korisnik1.setFirstName("Ljubica");
        korisnik1.setLastName("Vlahovic");
        korisnik1.setUsername("vlahoviclj");

        CreditCard cc1 = new CreditCard();
        cc1.setOwner("ljuba");
        cc1.setNumber("111-222-333");
        cc1.setExpMonth("04");
        cc1.setExpYear("2018");

        korisnik1.addBillingDetails(cc1);

        User korisnik2 = new User();
        korisnik2.setFirstName("Maja");
        korisnik2.setLastName("Jovic");
        korisnik2.setUsername("majamaja");

        BankAccount ba1 = new BankAccount();
        ba1.setOwner("ma");
        ba1.setAccount("acc1");
        ba1.setBankname("UniCredit");
        ba1.setSwift("ABCd");

        korisnik2.addBillingDetails(ba1);

        User korisnik3 = new User();
        korisnik3.setFirstName("Stefan");
        korisnik3.setLastName("Stefanov");
        korisnik3.setUsername("stefann");

        CreditCard cc2 = new CreditCard();
        cc2.setOwner("stef");
        cc2.setNumber("222-333-444");
        cc2.setExpMonth("01");
        cc2.setExpYear("2022");

        BankAccount ba2 = new BankAccount();
        ba2.setOwner("stef");
        ba2.setAccount("acc2");
        ba2.setBankname("Alta");
        ba2.setSwift("DCBa");

        korisnik3.addBillingDetails(cc2);
        korisnik3.addBillingDetails(ba2);
        
    }

    //Napisati upit koji vraća sve BillingDetail

    public static void vratiSveBillding() {

        emf = HibernateUtil.createEntityManagerFactory();
            EntityManager em = emf.createEntityManager();

            List<BillingDetails> billingDetails = new ArrayList<>();
            Query query = em.createQuery("select b from BillingDetails b", BillingDetails.class);
            billingDetails = query.getResultList();
            System.out.println("svi BillingDetails: " + billingDetails);

}

    //Napisati upit koji vraća samo credit card redove

    public static void vratiSamoRedoveCreditCard(){

            emf = HibernateUtil.createEntityManagerFactory();
            EntityManager em=emf.createEntityManager();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<CreditCard> cq = cb.createQuery(CreditCard.class);
            Root<CreditCard> root = cq.from(CreditCard.class);
            cq.select(root);
            TypedQuery<CreditCard> query = em.createQuery(cq);
             System.out.println("svi redovi: "+ query.getResultList());

    }

    //Napisati upit koji vraća samo bank account redove

    public static void vratiSamoBankAccRedobe(){

        emf=HibernateUtil.createEntityManagerFactory();
        EntityManager em=emf.createEntityManager();

        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<BankAccount> cq=cb.createQuery(BankAccount.class);
        Root<BankAccount> root=cq.from(BankAccount.class);
        cq.select(root);
        TypedQuery<BankAccount> query=em.createQuery(cq);
        System.out.println("svi BC redovi: "+query.getResultList());

    }

    //Korisniku broj 1 dodati bank account



}
