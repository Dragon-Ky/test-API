package com.example.day7springapi.service;

import com.example.day7springapi.exception.NotFoundException;
import com.example.day7springapi.exception.BadRequestException;

import com.example.day7springapi.model.Book;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookServiceV2 {
    private final Map<String,Book> map = new HashMap<>();

    public List<Book> findAll(){
        return new ArrayList<>(map.values());
    }

    public Book findById (String id){
        Book found = map.get(id);
        if (found == null ){
            throw new NotFoundException("Không tìm thấy : "+id);
        }
        return found;
    }

    public Book create(Book book){
        String id = book.getId().trim();
        if (map.containsKey(id)){
            throw new BadRequestException("Trùng ID : " + id);
        }
        map.put(id,book);
        return book;
    }

    public Book update  (String id,Book book){
        if (!map.containsKey(id)){
            throw new NotFoundException("không tìm thấy : " + id);
        }
        book.setId(id);
        map.put(id,book);
        return book;
    }

    public void delete(String id){
        if (!map.containsKey(id)){
            throw new NotFoundException("không tìm thấy : "+id);
        }
        map.remove(id);
    }
}
