package com.Bookstore.store_backend.service;

import com.Bookstore.store_backend.entity.Book;
import com.Bookstore.store_backend.repository.BookRepo;
import com.Bookstore.store_backend.util.bookImageUtils;  // Import the ImageUtils class
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

            // Compress the image using the utility method
            byte[] compressedImage = bookImageUtils.compressImage(imageBytes);

            // Print compressed image size
            System.out.println("Compressed Image Size: " + compressedImage.length + " bytes");

            // Set the compressed image in the book entity
            book.setImage(compressedImage);
        }

        // Print the book details (excluding the image)
        System.out.println("Saving book: " + book.getTitle() + " by " + book.getAuthor());

        // Save the book (with compressed image) to the repository (database)
        return bookRepo.save(book);  // Save the book to the repository (database)
    }
}
