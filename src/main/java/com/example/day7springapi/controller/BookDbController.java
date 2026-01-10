package com.example.day7springapi.controller;

import com.example.day7springapi.dto.BookUpdateRequest;
import com.example.day7springapi.dto.BookCreateRequest;

import com.example.day7springapi.entity.BookEntity;

import com.example.day7springapi.service.BookDbService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
public class BookDbController {
    private final BookDbService service;
    public BookDbController(BookDbService service){
        this.service=service;
    }

    @GetMapping
    public List<BookEntity>getAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public BookEntity getById(@PathVariable String id){
        return service.findById(id);
    }

    @PostMapping
    public BookEntity create(@Valid @RequestBody BookCreateRequest request){
        return service.create(request);
    }

    @PutMapping("/{id}")
    public BookEntity update(@PathVariable String id,
                             @Valid @RequestBody BookUpdateRequest request){
        return service.update(id,request);
    }

    @DeleteMapping("/{id}")
    public BookEntity delete(@PathVariable String id){
        return delete(id);
    }
}
