package controller;

import lombok.RequiredArgsConstructor;
import dto.BookFResponse;
import dto.BookRequest;
import dto.BookResponse;
import dto.FindRequest;
import models.Book;
import service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public BookFResponse getByNameAndAuthor(@RequestBody FindRequest searchRequest) {
        return bookService.getBookByNameAndAuthor(searchRequest);
    }

    @GetMapping("/{category}")
    public List<BookResponse> getByCategory(@PathVariable("category") String category) {
        return bookService.getBooksByCategory(category);
    }

    @PostMapping
    public BookFResponse create(@RequestBody BookRequest book) {
        return bookService.addNewBook(book);
    }

    @PutMapping("{id}")
    public void update(@PathVariable("id") Long id, @RequestBody Book book) {
        bookService.updateBookById(id, book);
    }
}
