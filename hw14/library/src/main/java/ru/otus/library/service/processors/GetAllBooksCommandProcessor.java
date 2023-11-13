package ru.otus.library.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.library.converters.BookConverter;
import ru.otus.library.models.Book;
import ru.otus.library.service.BookService;
import ru.otus.library.service.IOService;
import ru.otus.library.service.menu.MenuOption;

import java.util.List;

@Component
public class GetAllBooksCommandProcessor implements CommandProcessor {

    private final BookService bookService;

    private final BookConverter converter;

    private final IOService ioService;

    private final MenuOption getAllBooksMenuOption;

    public GetAllBooksCommandProcessor(
            BookService bookService,
            IOService ioService,
            BookConverter converter,
            MenuOption getAllBooksMenuOption) {
        this.bookService = bookService;
        this.ioService = ioService;
        this.converter = converter;
        this.getAllBooksMenuOption = getAllBooksMenuOption;
    }

    @Override
    public void processCommand() {
        List<Book> books = bookService.getAll();
        String result = converter.convertBookToString(books);
        ioService.outputString(result);
    }

    @Override
    public MenuOption getProcessedCommandOption() {
        return getAllBooksMenuOption;
    }
}
