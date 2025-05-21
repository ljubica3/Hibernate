package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.domen.Item;
import org.example.domen.Item_;
import org.example.util.HibernateUtil;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.List;
import org.hibernate.type.BigDecimalType;

public class Zadatak11Service {

    private static EntityManagerFactory emf= HibernateUtil.createEntityManagerFactory();
    private static EntityManager em=emf.createEntityManager();


    public static void kreirajIteme(){

        em.getTransaction().begin();

        Item item1=new Item();
        item1.setName("naocare");
        em.persist(item1);

        Item item2=new Item();
        item2.setName("torba");
        em.persist(item2);

        Item item3=new Item();
        item3.setName("sat");
        em.persist(item3);

        Item item4=new Item();
        item4.setName("tursija");
        em.persist(item4);

        Item item5=new Item();
        item5.setName("Televizor");
        em.persist(item5);

        em.getTransaction().commit();

    }

    /// JPA Static Metamodel
    //Napisati upit koji pretražuje iteme koje počinju na slovo t.
    // Koristiti statički metamodel.

    public static void itemiNaSlovoT(){

        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<Item> query=cb.createQuery(Item.class);
        Root<Item> itemRoot=query.from(Item.class);

        query.where(cb.like(cb.lower(itemRoot.get(Item_.name)),"t%"));

        em.createQuery(query).getResultList();
    }

    /// Fallback SQL
    //Napisati upit koji vraća sve iteme koji počinju na slovo t
    //koristeći org.hibernate.jdbc.Work interfejs

    public static void itemNaSlovoTFallingback(){

        Session session=em.unwrap(Session.class);

        session.doWork(connection -> {
            String str="SELECT name FROM Item WHERE LOWER(name) like ?";
            try (var preparedStatement = connection.prepareStatement(str)) {
                preparedStatement.setString(1, "t%");

                try (var resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String name = resultSet.getString("name");
                        System.out.println("Item name: " + name);
                    }
                }
            } catch (SQLException e) {
               e.printStackTrace();
            }
        });
    }

    //Napisati upit koji vraća sve iteme.
    //Koristiti hibernate native API, native query i addEntity metodu kod mapiranja

    public static void sviItemiNativeUpit() {

        Session session=em.unwrap(Session.class);

            List<Item> itemi = session.createNativeQuery("SELECT * FROM ITEM")
                                      .addEntity("i",Item.class)
                                      .list();
        System.out.println("svi itemi: "+itemi);
    }

    //Napisati upit koji vraća sve iteme.
    //Koristiti hibernate native API, native query i mapiranje odraditi preko xml fajla - sql-result-set-mapping

    public static void sviItemiXmlMapiranje(){

        List<Item> itemi=em.createNativeQuery("SELECT * FROM ITEM", Item.class).getResultList();

        System.out.println("svi itemi: "+itemi);
    }

    //Napisati upit koji vraća sve iteme.
    //Koristiti hibernate native API, named query koji se nalazi u XML fajlu i mapiranje odraditi preko xml fajla

    public static void sviItemiNamedQuery(){

        List<Item> itemi=em.createNamedQuery("Item.sviItemiNamedQuery", Item.class).getResultList();

        System.out.println("svi itemi: "+itemi);
    }

    //Napisati hibernate native sql query koji vraca sve items i sumu bid-ova za svaki item
    //-Koristiti mixed mapiranje, dakle mapiranje na Item klasu i Sumu amount-a

    public static void itemiSaSumomBidova() {
        Session session = em.unwrap(Session.class);

        List<Object[]> rezultati = session
                .createNativeQuery("""
            SELECT i.*, SUM(b.amount) as total_amount
            FROM ITEM i
            LEFT JOIN BID b ON i.ID = b.ITEM_ID
            GROUP BY i.ID
        """)
                .addEntity("i", Item.class)
                .addScalar("total_amount", BigDecimalType.INSTANCE) // koristi odgovarajući Hibernate Type
                .getResultList();

        for (Object[] red : rezultati) {
            Item item = (Item) red[0];
            BigDecimal sumaBidova = (BigDecimal) red[1];

            System.out.println("Item: " + item.getName() + ", suma bidova: " + sumaBidova);
        }
    }

    /// Stored Procedure
    //Napisati proceduru koja vraca sve item-e koji pocinju na slovo t. - FIND_ITEMS_LIKE

    public static void findItemsLike(){

            Session session = em.unwrap(Session.class);

            session.doWork(connection -> {
                try (CallableStatement cs = connection.prepareCall("{ call FIND_ITEMS_LIKE(?) }")) {
                    cs.setString(1, "t%");
                    boolean hasResultSet = cs.execute();
                    if (hasResultSet) {
                        try (ResultSet rs = cs.getResultSet()) {
                            while (rs.next()) {
                                String name = rs.getString("name");
                                System.out.println("Item name: " + name);
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }



}
