package ru.otus.library.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.BookRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final GenreService genreService;

    private final AuthorService authorService;

    public BookServiceImpl(BookRepository bookRepository, GenreService genreService, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @Transactional
    @Override
    public void createBook(String title, long authorId, long genreId) throws EntityNotFoundException {
        Author author = authorService.getById(authorId);
        Genre genre = genreService.getById(authorId);
        Book book = new Book(title, author, genre);
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void updateBookTitle(long id, String title) throws EntityNotFoundException {
        Optional<Book> optionalBook = bookRepository.getById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(title);
            bookRepository.save(book);
        } else {
            throw new EntityNotFoundException("Book is not found");
        }
    }

    @Override
    public Book getById(long id) throws EntityNotFoundException {
        Optional<Book> book = bookRepository.getById(id);
        if (book.isPresent()) {
            return book.get();
        }
        throw new EntityNotFoundException("Book is not found");
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAllCommentsByBookId(long id) {
        Optional<Book> optionalBook = bookRepository.getById(id);
        return optionalBook.map(book -> book.getComments().stream().toList())
                .orElse(
                        Collections.emptyList()
                );
    }

    @Transactional
    @Override
    public void deleteById(long bookId) {
        bookRepository.deleteById(bookId);
    }
}
