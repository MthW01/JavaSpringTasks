package service;

import dto.BookResponse;
import lombok.RequiredArgsConstructor;
import dto.BookFResponse;
import dto.BookRequest;
import dto.FindRequest;
import repository.BookRepository;
import repository.CategoryRepository;
import models.Book;
import models.Category;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper mapper;

    @Cacheable(value = "books", key = "#findRequest.bookName + #findRequest.author")
    public BookFResponse getBookByNameAndAuthor(FindRequest findRequest) {
        return mapper.bookToFullRs(
                bookRepository.findByNameAndAuthor(findRequest.bookName(), findRequest.author())
                        .orElseThrow(() -> new RuntimeException("Book not found"))
        );
    }

    @CacheEvict(value = {"books", "booksByCategory"}, allEntries = true)
    public BookFResponse addNewBook(BookRequest bookRequest) {
        Category category = categoryRepository.findById(bookRequest.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Book book = new Book(bookRequest.bookName(), bookRequest.author(), category);
        bookRepository.save(book);

        return mapper.bookToFullRs(book);
    }

    @CacheEvict(value = {"books", "booksByCategory"}, key = "#id", beforeInvocation = true)
    public void updateBookById(Long id, Book book) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        existingBook.setName(book.getBookName());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setCategory(book.getCategory());

        bookRepository.save(existingBook);
    }

    @CacheEvict(value = {"books", "booksByCategory"}, key = "#id", beforeInvocation = true)
    public void removeBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found");
        }
        bookRepository.deleteById(id);
    }

    @Cacheable(value = "booksByCategory", key = "#category")
    public List<BookResponse> getBooksByCategory(String category) {
        return categoryRepository.findAll()
                .stream()
                .filter(it -> it.getName().equalsIgnoreCase(category))
                .flatMap(e -> e.getBooks().stream())
                .map(mapper::bookToRs)
                .toList();
    }
}
