package ru.abtank.persistance;

public class Product {

    private int id;
    private String name;
    private String description;
    private String price;

    public Product(int id, String name, String description, String price) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.price = price;
    }

    public int getId() {
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
