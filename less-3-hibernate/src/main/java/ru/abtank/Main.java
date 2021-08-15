package ru.abtank;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class Main {


    public static void main(String[] args) {
//        Фабрика 1 на 1 БД
        EntityManagerFactory managerFactory = new Configuration()
                .configure("hibernate.org.xml")
                .buildSessionFactory();

        EntityManager em = managerFactory.createEntityManager();
//INSERT
//        Создание нового пользователя
        User user1 = new User(null, "alex1", "alex1", "alex1@bk.ru");
        User user2 = new User(null, "alex2", "alex2", "alex2@bk.ru");
        User user3 = new User(null, "alex3", "alex3", "alex3@bk.ru");
//        Открыли транзакцию
        em.getTransaction().begin();
//        добавили пользователя
//        em.persist(user1);
//        em.persist(user2);
//        em.persist(user3);
        em.getTransaction().commit();
//        em.close();

//SELECT

        User user11 = em.find(User.class, 1);
        System.out.println(user11);

        List<User> users = em.createQuery("FROM User", User.class).getResultList();
        System.out.println(users);

        User user12 = em.createQuery("FROM User Where login = :login", User.class)
                .setParameter("login", "alex3")
                .getSingleResult();
        System.out.println(user12);

//UPDATE //DELETE
        em.getTransaction().begin();
        User user22 = em.find(User.class, 2);
        user22.setLogin("ALEX22");
        em.getTransaction().commit();

        User user33 = em.find(User.class, 3);
        Contact mobile_phohe33 = new Contact(null, "mobile phohe", "12345678", user33);
        em.getTransaction().begin();
        em.persist(mobile_phohe33);
        em.getTransaction().commit();


        em.close();


    }
}
