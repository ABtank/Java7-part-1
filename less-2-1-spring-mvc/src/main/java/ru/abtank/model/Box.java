package ru.abtank.model;

public class Box {
    private Long id;
    private String color;
    private int size;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Box(Long id, String color, int size) {
        this.id = id;
        this.color = color;
        this.size = size;
    }

    public Box() {
    }
}
