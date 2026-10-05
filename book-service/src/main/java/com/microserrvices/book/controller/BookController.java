package com.microserrvices.book.controller;

import com.microserrvices.book.model.Book;
import com.microserrvices.book.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks(
            @RequestHeader(value = "X-Gateway", required = false) String gatewayHeader) {

        System.out.println("X-Gateway = " + gatewayHeader);

        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.addBook(book));
    }

    @PostMapping("/{id}/borrow")
    public ResponseEntity<String> borrowBook(@PathVariable String id) {
        boolean success = bookService.decrementCopies(id);
        if (success) {
            return ResponseEntity.ok("Book borrowed successfully");
        }
        return ResponseEntity.badRequest().body("Book copy not available or book not found");
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<String> returnBook(@PathVariable String id) {
        boolean success = bookService.incrementCopies(id);
        if (success) {
            return ResponseEntity.ok("Book returned successfully");
        }
        return ResponseEntity.badRequest().body("All copies are already returned or book not found");
    }
}
