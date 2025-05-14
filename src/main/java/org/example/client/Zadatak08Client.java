package org.example.client;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.service.Zadatak06Service;
import org.example.service.Zadatak08Service;

public class Zadatak08Client {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ljubica-pu");
        EntityManager em = emf.createEntityManager();

        Zadatak08Service.kreirajKorisnika(em);

        //sve bill det
//        Zadatak08Service.vratiSveBillding();

//       Zadatak08Service.vratiSamoRedoveCreditCard();

//        Zadatak08Service.vratiSamoBankAccRedobe();

        Zadatak08Service.vratiSveKorisnikeIspisiBillingDetails();

        Zadatak08Service.dodajBankAccKorisniku1();
        Zadatak08Service.obrisatiCreditCardKorisniku3();

        Zadatak08Service.vratiSveKorisnikeIspisiBillingDetails();
    }
}
