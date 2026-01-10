package com.example.day7springapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class BookEntity {
    @Id
    @Column(length = 50)
    private String id;

    @Column(nullable = false,length = 200)
    private String title;

    @Column(nullable = false,length = 150)
    private String author;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private  int quantity;

    public BookEntity(){}

    public BookEntity(String id,String title,String author,double price,int quantity){
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
