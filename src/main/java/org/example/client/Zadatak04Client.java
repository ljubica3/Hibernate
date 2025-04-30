package org.example.client;

import org.example.service.Zadatak04Service;

public class Zadatak04Client {
    public static void main(String[] args) {
        Zadatak04Service service = new Zadatak04Service();

        service.saveItem();

        Long userId = service.createUserWithAddresses();

        service.addCreditCard(userId);

        service.createBid(1L,userId);

        service.addSeller(2L);
    }
}
