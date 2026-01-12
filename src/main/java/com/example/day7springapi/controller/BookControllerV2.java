package com.example.day7springapi.controller;

import com.example.day7springapi.dto.BookCreateRequest;
import com.example.day7springapi.dto.BookUpdateRequest;

import com.example.day7springapi.model.Book;

import com.example.day7springapi.service.BookServiceV2;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookControllerV2 {
    private final BookServiceV2 serviceV2;

    public BookControllerV2(BookServiceV2 bookServiceV2){
        this.serviceV2 =bookServiceV2;
    }

    @GetMapping
    public List<Book>getAll(){
        return serviceV2.findAll();
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable String id){
        return serviceV2.findById(id);
    }

    @PostMapping
    public Book create(@Valid @RequestBody BookCreateRequest request){
        Book book = new Book(
                request.getId().trim(),
                request.getTitle().trim(),
                request.getAuthor().trim(),
                request.getPrice(),
                request.getQuantily()
        );
        return serviceV2.create(book);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable String id ,@Valid @RequestBody BookUpdateRequest request){
        Book book = new Book(
                id,
                request.getTitle().trim(),
                request.getAuthor().trim(),
                request.getPrice(),
                request.getQuantily()
        );
        return serviceV2.update(id,book);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id){
        serviceV2.delete("id");
        return "Đã xóa " + id;
    }
}
