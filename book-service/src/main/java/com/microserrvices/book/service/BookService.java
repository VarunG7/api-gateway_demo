package com.microserrvices.book.service;

import com.microserrvices.book.model.Book;
import com.microserrvices.book.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void initDefaultData() {
        if (bookRepository.count() == 0) {
            Book sample1 = new Book(UUID.randomUUID().toString(), "Effective Java", "Joshua Bloch", "978-0134685991", 5, 5);
            Book sample2 = new Book(UUID.randomUUID().toString(), "Clean Code", "Robert C. Martin", "978-0132350884", 3, 3);
            bookRepository.saveAll(List.of(sample1, sample2));
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(String bookId) {
        return bookRepository.findById(bookId);
    }

    @Transactional
    public Book addBook(Book book) {
        if (book.getId() == null || book.getId().isBlank()) {
            book.setId(UUID.randomUUID().toString());
        }
        if (book.getAvailableCopies() <= 0 && book.getTotalCopies() > 0) {
            book.setAvailableCopies(book.getTotalCopies());
        }
        return bookRepository.save(book);
    }

    @Transactional
    public synchronized boolean decrementCopies(String bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (book.getAvailableCopies() > 0) {
                book.setAvailableCopies(book.getAvailableCopies() - 1);
                bookRepository.save(book);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public synchronized boolean incrementCopies(String bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (book.getAvailableCopies() < book.getTotalCopies()) {
                book.setAvailableCopies(book.getAvailableCopies() + 1);
                bookRepository.save(book);
                return true;
            }
        }
        return false;
    }
}
