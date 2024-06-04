package com.mysite.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    public boolean reservedByCurrentUser;
    public boolean rentedByCurrentUser;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private String description;
    private String image;
    private Boolean rentAvailable;
    private Boolean rsvAvailable;


    // Getters and Setters
    //    public String getIsbn() {
    //        return isbn;
    //    }
    //
    //    public void setIsbn(String isbn) {
    //        this.isbn = isbn;
    //    }
    //
    //    public String getTitle() {
    //        return title;
    //    }
    //
    //    public void setTitle(String title) {
    //        this.title = title;
    //    }
    //
    //    public String getAuthor() {
    //        return author;
    //    }
    //
    //    public void setAuthor(String author) {
    //        this.author = author;
    //    }
    //
    //    public String getPublisher() {
    //        return publisher;
    //    }
    //
    //    public void setPublisher(String publisher) {
    //        this.publisher = publisher;
    //    }
    //
    //    public String getDescription() {
    //        return description;
    //    }
    //
    //    public void setDescription(String description) {
    //        this.description = description;
    //    }
    //
    //    public String getImage() {
    //        return image;
    //    }
    //
    //    public void setImage(String image) {
    //        this.image = image;
    //    }
}
