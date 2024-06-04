package com.mysite.library.service;

import com.mysite.library.entity.Book;
import com.mysite.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Optional<Book> findById(String isbn) {
        return bookRepository.findById(isbn);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(String isbn) {
        bookRepository.deleteById(isbn);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Page<Book> searchBooks(String searchBy, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        switch (searchBy.toLowerCase()) {
            case "isbn":
                return bookRepository.findByIsbnContaining(keyword, pageable);
            case "title":
                return bookRepository.findByTitleContaining(keyword, pageable);
            case "author":
                return bookRepository.findByAuthorContaining(keyword, pageable);
            case "publisher":
                return bookRepository.findByPublisherContaining(keyword, pageable);
            case "description":
                return bookRepository.findByDescriptionContaining(keyword, pageable);
            default:
                throw new IllegalArgumentException("Invalid search criteria: " + searchBy);
        }
    }

    public Page<Book> findBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

}
