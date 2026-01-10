package com.example.day7springapi.repository;

import com.example.day7springapi.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity,String> {
    boolean existsByTitleIgnoreCase(String title);
}
