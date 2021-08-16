package ru.abtank;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table (name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigDecimal price;

    @Column
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    public OrderItem() {
    }

    public OrderItem(Long id, Order order, Product product, Integer count) {
        this.id = id;
        this.price = product.getPrice();
        this.order = order;
        this.product = product;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return (count > 0)?price.multiply(new BigDecimal(count)):price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.price = product.getPrice();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", price=" + getPrice() +
                ", count=" + count +
                ", order=" + order.getId() +
                ", product=" + product +
                '}';
    }
}
