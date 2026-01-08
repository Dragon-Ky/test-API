package com.example.day7springapi.service;

import com.example.day7springapi.model.Book;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {
        private final Map<String,Book> map = new HashMap<>();

        public List<Book> findAll(){
            return new ArrayList<>(map.values());
        }

        public Book findById(String id){
            return map.get(id);
        }

        public boolean add(Book book){
            if (book == null) return false;

            if (book.getId() == null || book.getId().trim().isEmpty())return false;
            if (book.getTitle() == null || book.getTitle().trim().isEmpty() )return false;
            if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) return false;
            if (book.getPrice() < 0 ) return false;
            if (book.getQuantity() < 0 )return false;

            String id  = book.getId().trim();
            if (map.containsKey(id))return false;

            map.put(id,book);
            return true;
        }

        public boolean update(String id ,Book book){
            if (!map.containsKey(id)) return false;
            book.setId(id);
            map.put(id,book);
            return true;
        }
        public boolean delete(String id){
            return map.remove(id) != null   ;
        }
}
