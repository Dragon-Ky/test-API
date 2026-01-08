package com.example.day7springapi.controller;

import com.example.day7springapi.model.Book;
import com.example.day7springapi.service.BookService;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService service ;

    public BookController(BookService service){
        this.service =service;
    }

    @GetMapping
    public List<Book> getAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Book found =service.findById(id);
        if (found == null ) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(found);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Book book){
        boolean ok = service.add(book);
        if (!ok) return ResponseEntity.badRequest().body("Lỗi data hoặc trùng id");
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Book book) {
        boolean ok = service.update(id, book);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        boolean ok = service.delete(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.ok("Đã xóa");
    }


}
