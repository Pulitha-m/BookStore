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
@CrossOrigin("http://localhost:5176")
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

        Book book = Book.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .publishedDate(LocalDate.parse(publishedDate)) // Assuming "yyyy-MM-dd"
                .isbn(isbn)
                .price(price)
                .description(description)
                .category(category)
                .stockQuantity(stockQuantity)
                .build();

        Book savedBook = bookService.saveBook(book, imageFile);

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateBook/{bookId}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long bookId,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("publisher") String publisher,
            @RequestParam("publishedDate") String publishedDate,
            @RequestParam("isbn") String isbn,
            @RequestParam("price") BigDecimal price,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("stockQuantity") Integer stockQuantity,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) throws IOException {

        // Create a new book object with the updated details
        Book updatedBook = Book.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .publishedDate(LocalDate.parse(publishedDate)) // Assuming "yyyy-MM-dd"
                .isbn(isbn)
                .price(price)
                .description(description)
                .category(category)
                .stockQuantity(stockQuantity)
                .build();

        // Pass the updated book and image file (if any) to the service method
        Book savedBook = bookService.updateBook(updatedBook, bookId, imageFile);

        return savedBook != null ? ResponseEntity.ok(savedBook) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("deleteBook/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long bookId) {
        String responseMessage = bookService.deleteBookById(bookId);

        if (responseMessage.contains("success")) {
            return ResponseEntity.ok(responseMessage); // Return 200 OK with success message
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage); // Return 404 Not Found with error message
        }
    }

}
