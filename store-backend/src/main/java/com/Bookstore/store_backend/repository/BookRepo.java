package com.Bookstore.store_backend.repository;

import com.Bookstore.store_backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book,Long> {


}
