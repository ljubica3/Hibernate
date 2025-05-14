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

    private static EntityManagerFactory emf = HibernateUtil.createEntityManagerFactory();
    private static EntityManager em = emf.createEntityManager();


    public static void kreirajKorisnika(EntityManager em) {

        em.getTransaction().begin();
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

        em.persist(korisnik1);
        em.persist(korisnik2);
        em.persist(korisnik3);

        em.getTransaction().commit();

    }

    //Napisati upit koji vraća sve BillingDetail

    public static void vratiSveBillding() {

        List<BillingDetails> billingDetails = new ArrayList<>();
        Query query = em.createQuery("select b from BillingDetails b", BillingDetails.class);
        billingDetails = query.getResultList();
        System.out.println("svi BillingDetails: " + billingDetails.size() + " (ocekujemo 4)");

    }

    //Napisati upit koji vraća samo credit card redove

    public static void vratiSamoRedoveCreditCard() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CreditCard> cq = cb.createQuery(CreditCard.class);
        Root<CreditCard> root = cq.from(CreditCard.class);
        cq.select(root);
        TypedQuery<CreditCard> query = em.createQuery(cq);
        System.out.println("svi redovi: " + query.getResultList().size() + " (TREBA 2)");

        CriteriaQuery<User> cqUser = cb.createQuery(User.class);
        Root<User> rootUser = cqUser.from(User.class);
        cqUser.select(rootUser);
        TypedQuery<User> queryUser = em.createQuery(cqUser);
        System.out.println("svi useri: " + queryUser.getResultList().size() + " (TREBA 3)");

    }

    //Napisati upit koji vraća samo bank account redove

    public static void vratiSamoBankAccRedobe() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BankAccount> cq = cb.createQuery(BankAccount.class);
        Root<BankAccount> root = cq.from(BankAccount.class);
        cq.select(root);
        TypedQuery<BankAccount> query = em.createQuery(cq);
        System.out.println("svi BC redovi: " + query.getResultList().size() + " (treba 2)");

    }

    //Korisniku broj 1 dodati bank account

    public static void dodajBankAccKorisniku1() {

        User korisnik1 = em.find(User.class, 1L);

        BankAccount ba3 = new BankAccount();
        ba3.setOwner("korisnik 1");
        ba3.setAccount("111-222-333-444");
        ba3.setBankname("Intesa");
        ba3.setSwift("intesa");

        ba3.setOwnerUser(korisnik1);
        korisnik1.addBillingDetails(ba3);
        em.persist(korisnik1);

    }

    public static void obrisatiCreditCardKorisniku3() {

        User korisnik3 = em.find(User.class, 3L);

        if (korisnik3 != null) {
            List<BillingDetails> lista = korisnik3.getBillingDetails();

            BillingDetails brisanje = null;
            for (BillingDetails bd : lista) {
                if (bd instanceof CreditCard) {
                    brisanje = bd;
                    break;
                }
            }


            /// CASCADE DELETE
            // em.remove(korisnik3); -> ovo bi obrisalo i karticu

            ///  orphan removal
            korisnik3.getBillingDetails().remove(brisanje);
            em.persist(korisnik3); //ovo brise karticu
        }
    }

    //Vratiti svakog korisnika i ispisati njegove billing detalje na konzoli

    public static void vratiSveKorisnikeIspisiBillingDetails() {

        List<User> korisnici = em.createQuery("select u from User u", User.class)
                .getResultList();

        for (User korisnik : korisnici) {
            System.out.println("korisnik: " + korisnik.getUsername());

            List<BillingDetails> detalji = korisnik.getBillingDetails();

            if (detalji.isEmpty()) {
                System.out.println("nema detalja");
            } else {
                for (BillingDetails bd : detalji) {

                    if (bd instanceof CreditCard) {
                        CreditCard cc = (CreditCard) bd;
                        System.out.println("creditCard: " + cc.getNumber() + "mesec: " + cc.getExpMonth() + "godina: " + cc.getExpYear());
                    } else if (bd instanceof BankAccount) {
                        BankAccount ba = (BankAccount) bd;
                        System.out.println("bankAccount: " + ba.getAccount() + "bankName: " + ba.getBankname() + "swift: " + ba.getSwift());
                    }
                }
            }
        }
    }
}







