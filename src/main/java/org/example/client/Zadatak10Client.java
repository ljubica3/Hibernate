package org.example.client;

import org.example.service.Zadatak10Service;


public class Zadatak10Client {

    public static void main(String[] args) throws InterruptedException {

        Zadatak10Service.dodajPodatke();
//        Zadatak10Service.optimisticLockVersioning();
        Zadatak10Service.isolationLevel();

    }
}
