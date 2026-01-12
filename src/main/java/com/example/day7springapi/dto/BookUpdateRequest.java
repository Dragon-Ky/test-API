package com.example.day7springapi.dto;

import jakarta.validation.constraints.*;
public class BookUpdateRequest {
    @NotBlank(message = "Yêu cầu id")
    private String id;

    @NotBlank(message = "Yêu cầu tiêu đề")
    private String title;

    @NotBlank(message = "Yêu cầu tên tác giả")
    private  String author;

    @PositiveOrZero(message = "Giá tiền phải lớn hơn 0")
    private double price;

    @PositiveOrZero(message = "Số lượng phải lớn hơn 0")
    private  int quantily;

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

    public int getQuantily() {
        return quantily;
    }

    public void setQuantily(int quantily) {
        this.quantily = quantily;
    }
}
