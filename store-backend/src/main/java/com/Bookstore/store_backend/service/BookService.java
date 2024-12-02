package com.Bookstore.store_backend.service;

import com.Bookstore.store_backend.entity.Book;
import com.Bookstore.store_backend.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    // Save a new book with an image
    public Book saveBook(Book book, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            byte[] imageBytes = imageFile.getBytes();
            System.out.println("Original Image Size: " + imageBytes.length + " bytes");
            book.setImage(imageBytes);
        }
        System.out.println("Saving book: " + book.getTitle() + " by " + book.getAuthor());
        return bookRepo.save(book);
    }

    // Retrieve all books
    public List<Book> getAllBooks() {
        List<Book> books = bookRepo.findAll();
        System.out.println("Total books retrieved: " + books.size());

        // Convert image bytes to Base64 for frontend
        books.forEach(book -> {
            if (book.getImage() != null) {
                book.setImageBase64(Base64.getEncoder().encodeToString(book.getImage()));
            }
        });
        return books;
    }

    // Retrieve a single book by ID
    public Book getBookById(Long bookId) {
        Optional<Book> optionalBook = bookRepo.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            System.out.println("Book retrieved: " + book.getTitle());
            if (book.getImage() != null) {
                book.setImageBase64(Base64.getEncoder().encodeToString(book.getImage()));
            }
            return book;
        } else {
            System.out.println("Book not found with ID: " + bookId);
            return null;
        }
    }

    public Book updateBook(Book newBook, Long id, MultipartFile imageFile) throws IOException {
        Optional<Book> optionalBook = bookRepo.findById(id);
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();

            // Update the book's fields if they are not null
            existingBook.setTitle(newBook.getTitle() != null ? newBook.getTitle() : existingBook.getTitle());
            existingBook.setAuthor(newBook.getAuthor() != null ? newBook.getAuthor() : existingBook.getAuthor());
            existingBook.setDescription(newBook.getDescription() != null ? newBook.getDescription() : existingBook.getDescription());
            existingBook.setPrice(newBook.getPrice() != null ? newBook.getPrice() : existingBook.getPrice());
            existingBook.setCategory(newBook.getCategory() != null ? newBook.getCategory() : existingBook.getCategory());
            existingBook.setIsbn(newBook.getIsbn() != null ? newBook.getIsbn() : existingBook.getIsbn());
            existingBook.setPublisher(newBook.getPublisher() != null ? newBook.getPublisher() : existingBook.getPublisher());
            existingBook.setPublishedDate(newBook.getPublishedDate() != null ? newBook.getPublishedDate() : existingBook.getPublishedDate());

            // Update stockQuantity if a new value is provided
            if (newBook.getStockQuantity() != null) {
                existingBook.setStockQuantity(newBook.getStockQuantity());
            }
            // Update image if a new file is provided
            if (imageFile != null && !imageFile.isEmpty()) {
                byte[] imageBytes = imageFile.getBytes();
                System.out.println("Updated Image Size: " + imageBytes.length + " bytes");
                existingBook.setImage(imageBytes);
            }

            Book updatedBook = bookRepo.save(existingBook);
            System.out.println("Book updated: " + updatedBook.getTitle() + " by " + updatedBook.getAuthor());
            return updatedBook;
        } else {
            System.out.println("Book not found with ID: " + id);
            return null;
        }
    }



    public String deleteBookById(Long bookId){
        if(!bookRepo.existsById(bookId)){
            return "Book with id "+bookId +" does not exist";
        }

        bookRepo.deleteById(bookId);
        return "Book with id "+bookId+ " has been deleted success";
    }

}
