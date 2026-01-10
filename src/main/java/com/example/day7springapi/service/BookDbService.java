package com.example.day7springapi.service;

import com.example.day7springapi.dto.BookCreateRequest;
import com.example.day7springapi.dto.BookUpdateRequest;
import com.example.day7springapi.entity.BookEntity;
import com.example.day7springapi.exception.BadRequestException;
import com.example.day7springapi.exception.NotFoundException;
import com.example.day7springapi.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookDbService {
    private final BookRepository repository;
     public BookDbService(BookRepository repository){
         this.repository=repository;
     }
     public List<BookEntity> findAll(){
         return repository.findAll();
     }

     public BookEntity findById(String id ){
         return repository.findById(id)
                 .orElseThrow(()-> new NotFoundException("Không tìm thấy id : " + id));
     }

     public BookEntity create(BookCreateRequest request){
         String id = request.getId().trim();

         if (repository.existsById(id)){
             throw new BadRequestException("Trùng ID : " + id);
         }
         BookEntity book = new BookEntity(
                 id,
                 request.getTiltle().trim(),
                 request.getAuthor().trim(),
                 request.getPrice(),
                 request.getQuantily()
         );
         return repository.save(book);
     }

     public BookEntity update(String id, BookUpdateRequest request){
         BookEntity book = findById(id);

         book.setTitle(request.getTiltle().trim());
         book.setAuthor(request.getAuthor().trim());
         book.setPrice(request.getPrice());
         book.setQuantity(request.getQuantily());

         return repository.save(book);
     }

     public void delete(String id){
         BookEntity book = findById(id);
         repository.delete(book);
     }


}
