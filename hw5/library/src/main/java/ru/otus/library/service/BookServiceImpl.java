package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.dao.BookDao;
import ru.otus.library.domain.Book;
import java.util.List;


@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void create(String title, long authorId, long genreId) {
        bookDao.create(title, authorId, genreId);
    }

    @Override
    public void update(long id, String title, long authorId, long genreId) {
        bookDao.update(id, title, authorId, genreId);
    }

    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    @Override
    public Book getById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public Book getByTitle(String title) {
        return bookDao.getByTitle(title);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }
}
