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

        Zadatak08Service service = new Zadatak08Service(em);

        service.kreirajKorisnika(em);

       // Zadatak08Service.vratiSamoRedoveCreditCard();

        Zadatak08Service.vratiSamoBankAccRedobe();
    }
}
