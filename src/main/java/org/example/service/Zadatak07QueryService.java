package org.example.service;

import jakarta.persistence.EntityManagerFactory;
import org.example.util.HibernateUtil;

public class Zadatak07QueryService {

    private EntityManagerFactory emf;

    public void Zadatak07Service(){
        emf = HibernateUtil.createEntityManagerFactory();
    }
}
