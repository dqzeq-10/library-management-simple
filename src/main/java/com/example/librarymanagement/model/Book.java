package com.example.librarymanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Books")
public class Book {
    @Id
    @Column(name = "BookID", length = 10)
    private String bookId;

    @Column(name = "Title", nullable = false, length = 255)
    private String title;

    @Column(name = "Author", length = 255)
    private String author;

    @Column(name = "Publisher", length = 255)
    private String publisher;

    @Column(name = "PublicationYear")
    private Integer publicationYear;

    @Column(name = "ISBN", length = 20)
    private String isbn;

    @Column(name = "CopiesAvailable")
    private Integer copiesAvailable;

    // Getters and Setters

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(Integer copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }
}