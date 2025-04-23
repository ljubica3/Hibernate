package zadatak02;

import org.apache.log4j.PropertyConfigurator;
import zadatak02.repository.UserRepository;

import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");

        UserRepository ur = new UserRepository();

        User u1 = new User(1, "Aleksa", "Petrovic");
        User u2 = new User(2, "Uros", "Jovic");
        User u3 = new User(3, "Ana", "Anic");

        ur.createUser(u1);
        ur.createUser(u2);
        ur.createUser(u3);

        logger.info("korisnici kreirani");

        User found = ur.getUserById(1);
        logger.info("pronadjen korisnik: " + found);

        u1.setFirst_name("Aleksa azuriran");
        ur.updateUser(u1);
        logger.info("korisnik azuriran: " + ur.getUserById(1));

        ur.deleteUser(1);
        logger.info("korisnik obrisan");

        if (!ur.existsById(1)) {
            logger.info("korisnik ID 1 ne postoji");
        }
    }
}


