package ru.otus.library.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.library.converters.BookConverter;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.models.Book;
import ru.otus.library.service.BookService;
import ru.otus.library.service.IOService;
import ru.otus.library.service.menu.MenuOption;

@Component
public class GetBookByIdCommandProcessor implements CommandProcessor {

    private final BookService bookService;

    private final BookConverter converter;

    private final IOService ioService;

    private final MenuOption getBookByIdMenuOption;

    public GetBookByIdCommandProcessor(
            BookService bookService,
            IOService ioService,
            BookConverter converter,
            MenuOption getBookByIdMenuOption) {
        this.bookService = bookService;
        this.ioService = ioService;
        this.converter = converter;
        this.getBookByIdMenuOption = getBookByIdMenuOption;
    }

    @Override
    public void processCommand() {
        long bookId = ioService.readLongWithPrompt("Input book id");
        try {
            Book book = bookService.getById(bookId);
            ioService.outputString(converter.convertBookToString(book));
        } catch (EntityNotFoundException ex) {
            ioService.outputString(ex.toString());
        }
    }

    @Override
    public MenuOption getProcessedCommandOption() {
        return getBookByIdMenuOption;
    }
}
