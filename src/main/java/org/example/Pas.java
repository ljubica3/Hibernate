package org.example;

public class Pas {
    String ime;
    static int brojOciju = 2;

    public Pas() { //prazan konstruktor se sam generise ako ne postoji nijedan drugi
    }

    public Pas(String ime) {
        this.ime = ime;
    }

    static void laj(){
        System.out.println("AV AV");
    }

    void pedstaviSe(){
        System.out.println("Ja sam " + ime);
    }

    public static void main(String[] args) {
        Pas pas = new Pas("ime");
    }
}

