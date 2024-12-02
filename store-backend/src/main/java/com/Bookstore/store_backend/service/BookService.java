package com.Bookstore.store_backend.service;

import com.Bookstore.store_backend.entity.Book;
import com.Bookstore.store_backend.repository.BookRepo;
import com.Bookstore.store_backend.util.bookImageUtils;  // Import the ImageUtils class
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    // Service method to save a new book with a compressed image
    public Book saveBook(Book book, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            // Get the image as a byte array
            byte[] imageBytes = imageFile.getBytes();

            // Print original image size
            System.out.println("Original Image Size: " + imageBytes.length + " bytes");

            // Set the original image in the book entity (without compression)
            book.setImage(imageBytes);
        }

        // Print the book details (excluding the image)
        System.out.println("Saving book: " + book.getTitle() + " by " + book.getAuthor());

        // Save the book (with original image) to the repository (database)
        return bookRepo.save(book);  // Save the book to the repository (database)
    }


    public List<Book> getAllBooks() {
        List<Book> books = bookRepo.findAll();  // This should return all books
        System.out.println("Total books retrieved: " + books.size()); // Check how many books are fetched

        // Convert image bytes to Base64 string for each book
        for (Book book : books) {
            if (book.getImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(book.getImage());
                book.setImageBase64(base64Image);  // Set the base64 string for frontend
            }
        }

        return books;
    }
}
