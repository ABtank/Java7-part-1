package ru.abtank.persist.entity;

public class Product {

    private Integer id;
    private String name;
    private String description;
    private String price;

    public Product(Integer id, String name, String description, String price) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.price = price;
    }

//    public Product(){}

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
