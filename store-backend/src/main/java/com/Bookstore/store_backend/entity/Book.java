package com.Bookstore.store_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String title;
    private String author;
    private String publisher;
    private LocalDate publishedDate;
    private String isbn;

    @Column(precision = 10, scale = 2)
    private BigDecimal price; // Changed to BigDecimal for accuracy in financial data

    private String description;
    private String category;

    @Lob
    @Column(name = "book_image", columnDefinition = "LONGBLOB")
    private byte[] image;

    private Integer stockQuantity; // Renamed for clarity
}
