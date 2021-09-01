package ru.abtank.h2;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public Item() {
    }

    public void print() {
        System.out.println("Item id = " + id + "; title = " + title);
    }
}
