package ru.abtank.crud;


import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.abtank.PrepareDataApp;

import javax.transaction.Transactional;
import java.util.Collections;

public class CrudApp {
    public static void main(String[] args) {
        PrepareDataApp.forcePrepareData();

        Long maxId = null;

        SessionFactory factory = new Configuration()
                .configure("configs/crud/hibernate.cfg.xml")
                .buildSessionFactory();

        Session session = null;
        try {
            System.out.println("============\n== CREATE ==\n============");

            session = factory.getCurrentSession();
            session.beginTransaction();

            SimpleItem newSimpleItem = new SimpleItem("Dragon statue", 100);
            newSimpleItem.setId(3L);
            System.out.println("Before save: " + newSimpleItem);
            session.saveOrUpdate(newSimpleItem);
            System.out.println("After save: " + newSimpleItem);
            session.getTransaction().commit();
            System.out.println("After save and commit: " + newSimpleItem);
//
            System.out.println("============\n=== READ ===\n============");
            session = factory.getCurrentSession();
            session.beginTransaction();
//
            SimpleItem simpleItemFromDb = session.get(SimpleItem.class, 1L);
            SimpleItem simpleItemFromDb2 = session.get(SimpleItem.class, 1L); // Повторное вычитывание объекта (будет вытащен из кеша)
            System.out.println(simpleItemFromDb);
////            session.close();
//
            session.getTransaction().commit();
//
////
//            System.out.println("============\n=== READ IN ANOTHER SESSION ===\n============");
            session = factory.getCurrentSession();
            session.beginTransaction();
            SimpleItem simpleItemFromDb3 = session.get(SimpleItem.class, 1L);
////            System.out.println(simpleItemFromDb);
            session.getTransaction().commit();

            System.out.println("============\n== UPDATE ==\n============");
            session = factory.getCurrentSession();
            session.beginTransaction();
            SimpleItem simpleItemForUpdate = session.get(SimpleItem.class, 1L);
            System.out.println("Loaded item with id #1: " + simpleItemForUpdate);
            simpleItemForUpdate.setPrice(simpleItemForUpdate.getPrice() + 100);
//            session.flush();
//            simpleItemForUpdate.setTitle(simpleItemForUpdate.getTitle() + "X");
//            System.out.println("Modified item: " + simpleItemForUpdate);
            session.getTransaction().commit();
//
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            SimpleItem simpleItem = new SimpleItem("Moon", 540);
//            simpleItem.setId(2L);
//            session.save(simpleItem);
//            session.getTransaction().commit();

//            System.out.println("============\n== UPDATE ==\n============");
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            maxId = session.createQuery("SELECT MAX(s.id) FROM SimpleItem s", Long.class).getSingleResult();
//            SimpleItem simpleItemForUpdate = session.createQuery("SELECT s FROM SimpleItem s WHERE s.id = :id", SimpleItem.class)
//                    .setParameter("id", maxId)
//                    .getSingleResult();
//            System.out.println("Loaded item with max(id): " + simpleItemForUpdate);
//            simpleItemForUpdate.setPrice(simpleItemForUpdate.getPrice() + 100);
//            System.out.println("Modified item: " + simpleItemForUpdate);
//            session.getTransaction().commit();
//
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            SimpleItem simpleItemAfterUpdate = session.get(SimpleItem.class, simpleItemForUpdate.getId());
//            System.out.println("Loaded item after update: " + simpleItemAfterUpdate);
//            session.getTransaction().commit();
////
//            System.out.println("============\n== DELETE ==\n============");
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            session.createQuery("SELECT s FROM SimpleItem s where s.id = :id", Long.class).setParameter("id", 1L).getSingleResult();
//            maxId = session.createQuery("SELECT MAX(s.id) FROM SimpleItem s", Long.class).getSingleResult();
//            session.delete(session.get(SimpleItem.class, maxId));
//            session.getTransaction().commit();
//
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            SimpleItem removedSimpleItem = session.get(SimpleItem.class, maxId);
//            System.out.println("Loaded item after remove: " + removedSimpleItem);
//            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            factory.close();
            if (session != null) {
                session.close();
            }
        }
    }
}
