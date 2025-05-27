package org.example.client;

import org.example.service.Zadatak04Service;
//import org.example.service.Zadatak06Service;
import org.example.service.Zadatak07QueryService;


public class Zadatak07Client {


    public static void main(String[] args) {
        Zadatak07QueryService service = new Zadatak07QueryService();
//        service.queryKorisnici();
//         service.queryKorisniciRestriction();
  //       service.queryKorisniciProjection();
//         service.queryItemBidJoin();
        service.queryItemTuple();
//        service.aliasToBeanResultTransformer();
    }

}
