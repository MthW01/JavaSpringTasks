package service;

import dto.BookFResponse;
import dto.BookResponse;
import models.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public BookFResponse bookToFullRs(Book book){
        return new BookFResponse(book.getName(), book.getCategory().getName(), book.getAuthor());
    }

    public BookResponse bookToRs(Book book){
        return new BookResponse(book.getName(), book.getAuthor());
    }
}
