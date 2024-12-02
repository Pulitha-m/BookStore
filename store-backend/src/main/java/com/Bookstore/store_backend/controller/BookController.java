package com.Bookstore.store_backend.controller;

import com.Bookstore.store_backend.entity.Book;
import com.Bookstore.store_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Endpoint to add a new book with an image
    @PostMapping("/add")
    public ResponseEntity<Book> addBook(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("publisher") String publisher,
            @RequestParam("publishedDate") String publishedDate,
            @RequestParam("isbn") String isbn,
            @RequestParam("price") BigDecimal price,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("stockQuantity") Integer stockQuantity,
            @RequestParam("image") MultipartFile imageFile) throws IOException {

        // Create a new Book object from the form data
        Book book = Book.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .publishedDate(LocalDate.parse(publishedDate))  // Assuming format is "yyyy-MM-dd"
                .isbn(isbn)
                .price(price)
                .description(description)
                .category(category)
                .stockQuantity(stockQuantity)
                .build();

        // Save the book along with the image
        Book savedBook = bookService.saveBook(book, imageFile);

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }


    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }


    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable Long bookId){

        return bookService.getBookById(bookId);

    }
}
