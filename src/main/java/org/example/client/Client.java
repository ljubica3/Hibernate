package org.example.client;

import org.example.service.Service;

public class Client {
    public static void main(String[] args) {
        Service service=new Service();

        service.save();

        Long userId=service.createUserWithAddresses();

        service.addCreditCard(userId);

        service.createBid(1L,userId);

        service.addSeller(2L);
    }
}
