package ru.abtank;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
//        Фабрика 1 на 1 БД
        EntityManagerFactory managerFactory = new Configuration()
                .configure("hibernate.org.xml")
                .buildSessionFactory();

        EntityManager em = managerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Contact").executeUpdate();
        em.createQuery("DELETE FROM OrderItem").executeUpdate();
        em.createQuery("DELETE FROM Order").executeUpdate();
        em.createQuery("DELETE FROM Product").executeUpdate();
        em.createQuery("DELETE FROM User").executeUpdate();
        em.getTransaction().commit();
//INSERT
//        Создание нового пользователя
        User user1 = new User(null, "alex1", "alex1", "alex1@bk.ru");
        User user2 = new User(null, "alex2", "alex2", "alex2@bk.ru");
        User user3 = new User(null, "alex3", "alex3", "alex3@bk.ru");
        User user4 = new User(null, "alex4", "alex4", "alex4@bk.ru");
//        Открыли транзакцию
        em.getTransaction().begin();
//        добавили пользователя
        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);
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

//UPDATE
        em.getTransaction().begin();
        User user22 = em.createQuery("FROM User Where login = :login", User.class)
                .setParameter("login", "alex2")
                .getSingleResult();
        user22.setLogin("ALEX22");
        em.getTransaction().commit();

//DELETE
        em.getTransaction().begin();
        em.remove(user4);
        em.getTransaction().commit();

//            User user33 = em.find(User.class, 3);
        User user33 = em.createQuery("FROM User Where login = :login", User.class)
                .setParameter("login", "alex3")
                .getSingleResult();
        Contact mobile_phohe33 = new Contact(null, "mobile phohe", "12345678", user33);
        em.getTransaction().begin();
        em.persist(mobile_phohe33);
        em.getTransaction().commit();


//        HW3
        Product product1 = new Product(null, "Product_1", new BigDecimal(100));
        Product product2 = new Product(null, "Product_2", new BigDecimal(200));
        Product product3 = new Product(null, "Product_3", new BigDecimal(300));
        Product product4 = new Product(null, "Product_4", new BigDecimal(400));


        em.getTransaction().begin();
        List<Product> products = em.createQuery("FROM Product", Product.class).getResultList();
        if (products.isEmpty()) {
            em.persist(product1);
            em.persist(product2);
            em.persist(product3);
            em.persist(product4);
        }
        products = em.createQuery("FROM Product", Product.class).getResultList();
        em.getTransaction().commit();


        em.getTransaction().begin();
        products = em.createQuery("FROM Product", Product.class).getResultList();
        if (!products.isEmpty()) {
            List<OrderItem> orderItems = new ArrayList<>();
            Order order1 = new Order(null, user1);
            em.persist(order1);
            OrderItem orderItem11 = new OrderItem(null, order1, products.get(1), 2);
            OrderItem orderItem12 = new OrderItem(null, order1, products.get(2), 3);
            OrderItem orderItem13 = new OrderItem(null, order1, products.get(3), 1);
            em.persist(orderItem11);
            em.persist(orderItem12);
            em.persist(orderItem13);
            order1.setOrderItems(em.createQuery("FROM OrderItem WHERE order_id=:id")
                    .setParameter("id", order1.getId())
                    .getResultList());

            Order order2 = new Order(null, user2);
            em.persist(order2);
            OrderItem orderItem21 = new OrderItem(null, order2, products.get(1), 5);
            OrderItem orderItem22 = new OrderItem(null, order2, products.get(2), 8);
            OrderItem orderItem23 = new OrderItem(null, order2, products.get(0), 1);
            em.persist(orderItem21);
            em.persist(orderItem22);
            em.persist(orderItem23);
            orderItems.add(orderItem21);
            orderItems.add(orderItem22);
            orderItems.add(orderItem23);
            System.out.println(orderItems);
            order2.setOrderItems(orderItems);

            Order order3 = new Order(null, user3);
            em.persist(order3);
            OrderItem orderItem31 = new OrderItem();
            em.persist(orderItem31);
            System.out.println("orderItem31=" + orderItem31.getId());
            orderItem31.setOrder(order3);
            orderItem31.setCount(9);
            orderItem31.setProduct(products.get(0));
            List<OrderItem> orderItems3 = new ArrayList<>();
            orderItems3.add(orderItem31);
            order3.setOrderItems(orderItems3);

        }
        em.getTransaction().commit();

        em.getTransaction().begin();
        users.clear();
        users = em.createQuery("FROM User", User.class).getResultList();
        for (User user: users) {
            List<Order> user_orders = em.createQuery("FROM Order WHERE user_id = :user_id", Order.class)
                    .setParameter("user_id", user.getId())
                    .getResultList();
            System.out.println(user.getLogin()+" have orders:\n" + user_orders);
        }
        em.getTransaction().commit();

        em.close();


    }
}
