package org.example.client;

import org.example.service.ServiceZadatak04;

public class ClientZadatak04 {
    public static void main(String[] args) {
        ServiceZadatak04 service = new ServiceZadatak04();

        service.saveItem();

        Long userId = service.createUserWithAddresses();

        service.addCreditCard(userId);

        service.createBid(1L,userId);

        service.addSeller(2L);
    }
}
